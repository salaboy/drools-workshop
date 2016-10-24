/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.misc.pitfalls;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;
import org.drools.workshop.misc.model.University;
import org.drools.workshop.misc.services.MyDataProviderServiceImpl;
import org.drools.workshop.misc.services.SlowMyServiceImpl;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.cdi.KBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class WrongServiceRulesJUnitTest {

    public WrongServiceRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.drools.workshop.model")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    @KBase("misc-wrongServiceKBase")
    private KieBase kBase;

    @Test
    public void testSlowServiceInAGlobal() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();

        kSession.setGlobal("myService", new SlowMyServiceImpl());
        System.out.println(" ---- Starting testSlowServiceInAGlobal() Test ---");

        List<Person> persons = generatePersons(20);
        for (Person p : persons) {
            kSession.insert(p);
        }

        Assert.assertEquals(20, kSession.fireAllRules());
        System.out.println(" ---- Finished testSlowServiceInAGlobal() Test ---");
        kSession.dispose();
    }

    @Test
    public void testServiceCallInRHS() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();

        kSession.setGlobal("myDataProviderService", new MyDataProviderServiceImpl());
        System.out.println(" ---- Starting testServiceCallInRHS() Test ---");

        University university = new University("My Uni");
        FactHandle univFactHandle = kSession.insert(university);

        Assert.assertEquals(50, kSession.fireAllRules());
        university.setName("My Uni (updated)");
        kSession.update(univFactHandle, university);
        Assert.assertEquals(50, kSession.fireAllRules());
        
        System.out.println(" ---- Finished testServiceCallInRHS() Test ---");
        kSession.dispose();
    }

    private List<Person> generatePersons(int amount) {
        List<Person> results = new ArrayList<Person>();
        for (int i = 0; i < amount; i++) {
            results.add(new Person("name " + i, (int) Math.round(Math.random()) % 100, "name" + i + "@mail.com", "Somewhere", ((i % 2) == 1) ? Gender.FEMALE : Gender.MALE));
        }
        return results;
    }
}
