/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.pitfalls;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.House;
import org.drools.workshop.misc.model.Room;

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
public class CrossProductRulesJUnitTest {

    public CrossProductRulesJUnitTest() {
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
    @KBase("clinical-crossProductKBase")
    private KieBase kBaseCrossProduct;

    @Inject
    @KBase("clinical-crossProductSolutionKBase")
    private KieBase kBaseCrossProductSolution;

    @Test
    public void testHouseWith3Rooms() {
        Assert.assertNotNull(kBaseCrossProduct);
        KieSession kSession = kBaseCrossProduct.newKieSession();
        System.out.println(" ---- Starting testHouseWith3Rooms() Test ---");

        Room livingRoom = new Room("living room", 10);
        kSession.insert(livingRoom);

        Room kitchenRoom = new Room("kitchen", 5);
        kSession.insert(kitchenRoom);

        Room bedRoom = new Room("bedroom", 4);
        kSession.insert(bedRoom);

        House house = new House();
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(livingRoom);
        rooms.add(bedRoom);
        rooms.add(kitchenRoom);
        house.setRooms(rooms);

        kSession.insert(house);

        Assert.assertEquals(3, kSession.fireAllRules());
        System.out.println(" ---- Finished testHouseWith3Rooms() Test ---");
        kSession.dispose();
    }

    @Test
    public void test2HousesWith3Rooms() {
        Assert.assertNotNull(kBaseCrossProduct);
        KieSession kSession = kBaseCrossProduct.newKieSession();
        System.out.println(" ---- Starting test2HousesWith3Rooms() Test ---");

        Room livingRoomA = new Room("living room", 10);
        kSession.insert(livingRoomA);

        Room kitchenRoomA = new Room("kitchen", 5);
        kSession.insert(kitchenRoomA);

        Room bedRoomA = new Room("bedroom", 4);
        kSession.insert(bedRoomA);

        House houseA = new House();
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(livingRoomA);
        rooms.add(bedRoomA);
        rooms.add(kitchenRoomA);
        houseA.setRooms(rooms);

        kSession.insert(houseA);

        Room livingRoomB = new Room("living room", 10);
        kSession.insert(livingRoomB);

        Room kitchenRoomB = new Room("kitchen", 5);
        kSession.insert(kitchenRoomB);

        Room bedRoomB = new Room("bedroom", 4);
        kSession.insert(bedRoomB);

        House houseB = new House();
        List<Room> roomsB = new ArrayList<Room>();
        roomsB.add(livingRoomB);
        roomsB.add(bedRoomB);
        roomsB.add(kitchenRoomB);
        houseB.setRooms(roomsB);

        kSession.insert(houseB);

        Assert.assertEquals(12, kSession.fireAllRules());
        System.out.println(" ---- Finished test2HousesWith3Rooms() Test ---");
        kSession.dispose();
    }

    @Test
    public void test2HousesWith3RoomsChecked() {
        Assert.assertNotNull(kBaseCrossProductSolution);
        KieSession kSession = kBaseCrossProductSolution.newKieSession();
        System.out.println(" ---- Starting test2HousesWith3RoomsChecked() Test ---");

        Room livingRoomA = new Room("living room", 10);
        kSession.insert(livingRoomA);

        Room kitchenRoomA = new Room("kitchen", 5);
        kSession.insert(kitchenRoomA);

        Room bedRoomA = new Room("bedroom", 4);
        kSession.insert(bedRoomA);

        House houseA = new House();
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(livingRoomA);
        rooms.add(bedRoomA);
        rooms.add(kitchenRoomA);
        houseA.setRooms(rooms);

        kSession.insert(houseA);

        Room livingRoomB = new Room("living room", 10);
        kSession.insert(livingRoomB);

        Room kitchenRoomB = new Room("kitchen", 5);
        kSession.insert(kitchenRoomB);

        Room bedRoomB = new Room("bedroom", 4);
        kSession.insert(bedRoomB);

        House houseB = new House();
        List<Room> roomsB = new ArrayList<Room>();
        roomsB.add(livingRoomB);
        roomsB.add(bedRoomB);
        roomsB.add(kitchenRoomB);
        houseB.setRooms(roomsB);

        kSession.insert(houseB);

        Assert.assertEquals(6, kSession.fireAllRules());
        System.out.println(" ---- Finished test2HousesWith3RoomsChecked() Test ---");
        kSession.dispose();
    }
   
}
