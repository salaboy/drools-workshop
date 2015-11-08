/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.model.House;
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
public class MultiConditionRulesJUnitTest {

    public MultiConditionRulesJUnitTest() {
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
    @KBase("rules03")
    private KieBase kBase;

    @Test
    public void testPersonAndARoom() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPersonAndARoom() Test ---");
        Room room = new Room("living room", 10);
        kSession.insert(room);
        Person person = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        kSession.insert(person);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testPersonAndARoom() Test ---");
        kSession.dispose();
    }

    @Test
    public void testPersonsAnd2Rooms() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPersonsAnd2Rooms() Test ---");
        Room livingRoom = new Room("living room 2", 10);
        kSession.insert(livingRoom);
        Room kitchen = new Room("kitchen", 3);
        kSession.insert(kitchen);
        Person person = new Person("salaboy 2", 32, "salaboy@mail.com", "London", Gender.MALE);
        kSession.insert(person);

        Assert.assertEquals(2, kSession.fireAllRules());
        System.out.println(" ---- Finished testPersonsAnd2Rooms() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void test2PersonsAnd2Rooms() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting test2PersonsAnd2Rooms() Test ---");
        Room livingRoom = new Room("living room", 10);
        kSession.insert(livingRoom);
        Room kitchen = new Room("kitchen", 3);
        kSession.insert(kitchen);
        Person salaboy = new Person("salaboy", 32, "salaboy@mail.com", "London", Gender.MALE);
        kSession.insert(salaboy);
        Person maryland = new Person("maryland", 25, "maryland@supreme.com", "USA", Gender.MALE);
        kSession.insert(maryland);

        Assert.assertEquals(4, kSession.fireAllRules());
        System.out.println(" ---- Finished test2PersonsAnd2Rooms() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void testRoomAndHouse() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRoomAndHouse() Test ---");
        Room livingRoom = new Room("living room", 10);
        kSession.insert(livingRoom);

        House house = new House();
        kSession.insert(house);

        Assert.assertEquals(0, kSession.fireAllRules());
        System.out.println(" ---- Finished testRoomAndHouse() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void testRoomAndHouseRelated() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRoomAndHouseRelated() Test ---");
        Room livingRoom = new Room("living room", 10);
        kSession.insert(livingRoom);

        House house = new House();
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(livingRoom);
        house.setRooms(rooms);
        kSession.insert(house);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testRoomAndHouseRelated() Test ---");
        kSession.dispose();
    }
   
    
//    private List<Person> generatePersons(int amount) {
//        List<Person> results = new ArrayList<Person>();
//        for (int i = 0; i < amount; i++) {
//            results.add(new Person("name " + i, (int) Math.round(Math.random()) % 100, "name" + i + "@mail.com", "Somewhere", ((i % 2) == 1) ? Gender.FEMALE : Gender.MALE));
//        }
//        return results;
//    }

}
