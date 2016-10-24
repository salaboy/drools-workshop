/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;
import org.drools.workshop.misc.model.Room;
import org.drools.workshop.misc.services.MyServiceImpl;

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

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class UsingGlobalsRulesJUnitTest {

    public UsingGlobalsRulesJUnitTest() {
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
    @KBase("clinical-usingGlobalsKBase")
    private KieBase kBase;

    @Test
    public void testUsingGlobalToTrackRulesFiring() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        List<String> rulesFired = new ArrayList<String>();
        kSession.setGlobal("rulesFired", rulesFired);
        System.out.println(" ---- Starting testUsingGlobalToTrackRulesFiring() Test ---");
        Room room = new Room("living room", 10);
        kSession.insert(room);
        Person person = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        kSession.insert(person);

        Assert.assertEquals(1, kSession.fireAllRules());
        Assert.assertTrue(rulesFired.contains("room and a person"));
        System.out.println(" ---- Finished testUsingGlobalToTrackRulesFiring() Test ---");
        kSession.dispose();
    }

    @Test
    public void testServiceInAGlobal() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        
        kSession.setGlobal("myService", new MyServiceImpl());
        System.out.println(" ---- Starting testServiceInAGlobal() Test ---");
        Room room = new Room("living room", 10);
        
        List<Person> persons = generatePersons(20);
        room.setPersons(persons);
        kSession.insert(room);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testServiceInAGlobal() Test ---");
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
