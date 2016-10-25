/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.misc.features;


import java.util.Arrays;
import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;
import org.drools.workshop.misc.model.University;

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
public class SimpleInferenceRulesJUnitTest {

    public SimpleInferenceRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.drools.workshop.misc.model")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    @KBase("misc-simpleInferenceKBase")
    private KieBase kBase;

    @Test
    public void testYoungEnrolledStudentsGetBusTicketDiscount() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testEnrolledStudentsGetFreeBusTickets() Test ---");
        
        Person person = new Person("John", 29, "john@mail.com", "London", Gender.MALE);
        kSession.insert(person);
        
        University university = new University("My uni");
        university.setEnrolled(Arrays.asList(person));
        
        kSession.insert(university);
        
        Assert.assertEquals(2, kSession.fireAllRules());
        System.out.println(" ---- Finished testEnrolledStudentsGetFreeBusTickets() Test ---");
        kSession.dispose();
    }

    
   
   


}
