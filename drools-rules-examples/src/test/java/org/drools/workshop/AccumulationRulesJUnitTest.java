/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.model.Person;
import org.drools.workshop.model.Person.Gender;
import org.drools.workshop.model.Room;

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
public class AccumulationRulesJUnitTest {

    public AccumulationRulesJUnitTest() {
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
    @KBase("rules04")
    private KieBase kBase;

    @Test
    public void testPersonAndARoom() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPersonsInARoom() Test ---");
        Room room = new Room("living room", 10);
        kSession.insert(room);
        Person person = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        kSession.insert(person);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testPersonsInARoom() Test ---");
        kSession.dispose();
    }

    @Test
    public void testPersonInARoom() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPersonsInARoom() Test ---");
        Room room = new Room("living room", 10);
        Person salaboy = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        List<Person> persons = new ArrayList<Person>();
        persons.add(salaboy);
        room.setPersons(persons);
        kSession.insert(room);

        Assert.assertEquals(0, kSession.fireAllRules());
        System.out.println(" ---- Finished testPersonsInARoom() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void test20PersonsInARoom() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting test20PersonsInARoom() Test ---");
        Room room = new Room("living room", 10);
        List<Person> persons = generatePersons(20);
        room.setPersons(persons);
        kSession.insert(room);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished test20PersonsInARoom() Test ---");
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
