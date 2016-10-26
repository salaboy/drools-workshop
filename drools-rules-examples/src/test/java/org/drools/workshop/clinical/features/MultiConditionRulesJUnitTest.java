/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.House;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;
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
public class MultiConditionRulesJUnitTest {

    public MultiConditionRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.drools.workshop.clinical.model")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    @KBase("clinical-multiConditionsKBase")
    private KieBase kBase;

    @Test
    public void testPatientAndAnObservation() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPatientAndAnObservation() Test ---");
        
        kSession.insert(new Patient()
            .setId("Patient/1")
        );
        kSession.insert(new Observation()
            .setId("Observation/1")
        );
        
        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientAndAnObservation() Test ---");
        kSession.dispose();
    }

    
    @Test
    public void testPatientAndAnRelatedObservation() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRoomAndHouseRelated() Test ---");
        
        Patient patient = (Patient) new Patient().setId("Patient/1");
        kSession.insert(patient);
        kSession.insert(new Observation()
            .setSubject(new ResourceReferenceDt(patient))
            .setId("Observation/1")
        );

        Assert.assertEquals(2, kSession.fireAllRules());
        System.out.println(" ---- Finished testRoomAndHouseRelated() Test ---");
        kSession.dispose();
    }
   


}
