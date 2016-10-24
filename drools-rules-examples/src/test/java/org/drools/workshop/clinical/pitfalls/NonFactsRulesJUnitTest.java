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
public class NonFactsRulesJUnitTest {

    public NonFactsRulesJUnitTest() {
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
    @KBase("clinical-nonFactsKBase")
    private KieBase kBase;

    
    @Test
    public void testRoomAndHouse() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRoomAndHouse() Test ---");
        Room livingRoom = new Room("living room", 10);
        kSession.insert(livingRoom);

        House house = new House();
        kSession.insert(house);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testRoomAndHouse() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void testRoomAndHouseRelated() {
        
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRoomAndHouseRelated() Test ---");
        Room livingRoom = new Room("living room", 10);

        House house = new House();
        
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(livingRoom);
        house.setRooms(rooms);
        
        kSession.insert(house);

        Assert.assertEquals(0, kSession.fireAllRules());
        System.out.println(" ---- Finished testRoomAndHouseRelated() Test ---");
        kSession.dispose();
        
    }
   


}
