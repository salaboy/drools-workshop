/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.features;

import javax.inject.Inject;
import org.drools.workshop.model.Person;
import org.drools.workshop.model.Person.Gender;

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
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class TMSRulesJUnitTest {

    public TMSRulesJUnitTest() {
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
    @KBase("tmsKBase")
    private KieBase kBase;

    @Test
    public void testSimpleTMS() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testSimpleTMS() Test ---");
        kSession.addEventListener(new RuleRuntimeEventListener() {

            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println(" >>> Fact Inserted" + event );
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println(" >>> Fact Updated" + event );
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println(" >>> Fact Deleted" + event );
            }
        });
        Person p = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        FactHandle personFH = kSession.insert(p);

        kSession.fireAllRules();
        p.setCity("Paris");
        kSession.update(personFH, p);
        kSession.fireAllRules();

        System.out.println(" ---- Finished testSimpleTMS() Test ---");
        kSession.dispose();
    }

}
