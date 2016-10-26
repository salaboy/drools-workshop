/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.pitfalls;

import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
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
                .addPackages(true, "org.drools.workshop.clinical.model")
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
    public void testPatientWith3Observations() {
        Assert.assertNotNull(kBaseCrossProduct);
        KieSession kSession = kBaseCrossProduct.newKieSession();
        System.out.println(" ---- Starting testPatientWith3Observations() Test ---");

        Patient patient = (Patient) new Patient().setId("Patient/1");
        
        Observation observation1 = (Observation) new Observation().setId("Observation/1");
        observation1.setSubject(new ResourceReferenceDt(patient));
        
        Observation observation2 = (Observation) new Observation().setId("Observation/2");
        observation2.setSubject(new ResourceReferenceDt(patient));
        
        Observation observation3 = (Observation) new Observation().setId("Observation/3");
        observation3.setSubject(new ResourceReferenceDt(patient));

        kSession.insert(patient);
        kSession.insert(observation1);
        kSession.insert(observation2);
        kSession.insert(observation3);

        Assert.assertEquals(3, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientWith3Observations() Test ---");
        kSession.dispose();
    }

    @Test
    public void test2PatientsWith3Observations() {
        Assert.assertNotNull(kBaseCrossProduct);
        KieSession kSession = kBaseCrossProduct.newKieSession();
        System.out.println(" ---- Starting test2PatientsWith3Observations() Test ---");

        //1 Patient with 3 Observations
        Patient patient1 = (Patient) new Patient().setId("Patient/1");
        
        Observation observation1 = (Observation) new Observation().setId("Observation/1");
        observation1.setSubject(new ResourceReferenceDt(patient1));
        
        Observation observation2 = (Observation) new Observation().setId("Observation/2");
        observation2.setSubject(new ResourceReferenceDt(patient1));
        
        Observation observation3 = (Observation) new Observation().setId("Observation/3");
        observation3.setSubject(new ResourceReferenceDt(patient1));

        kSession.insert(patient1);
        kSession.insert(observation1);
        kSession.insert(observation2);
        kSession.insert(observation3);
        

        //Another Patient with 3 Observations
        Patient patient2 = (Patient) new Patient().setId("Patient/2");
        
        Observation observation4 = (Observation) new Observation().setId("Observation/4");
        observation4.setSubject(new ResourceReferenceDt(patient2));
        
        Observation observation5 = (Observation) new Observation().setId("Observation/5");
        observation5.setSubject(new ResourceReferenceDt(patient2));
        
        Observation observation6 = (Observation) new Observation().setId("Observation/6");
        observation6.setSubject(new ResourceReferenceDt(patient2));

        kSession.insert(patient2);
        kSession.insert(observation4);
        kSession.insert(observation5);
        kSession.insert(observation6);
        
        Assert.assertEquals(12, kSession.fireAllRules());
        System.out.println(" ---- Finished test2PatientsWith3Observations() Test ---");
        kSession.dispose();
    }

    @Test
    public void test2PatientsWith3ObservationsChecked() {
        Assert.assertNotNull(kBaseCrossProductSolution);
        KieSession kSession = kBaseCrossProductSolution.newKieSession();
        System.out.println(" ---- Starting test2PatientsWith3ObservationsChecked() Test ---");

        //1 Patient with 3 Observations
        Patient patient1 = (Patient) new Patient().setId("Patient/1");
        
        Observation observation1 = (Observation) new Observation().setId("Observation/1");
        observation1.setSubject(new ResourceReferenceDt(patient1));
        
        Observation observation2 = (Observation) new Observation().setId("Observation/2");
        observation2.setSubject(new ResourceReferenceDt(patient1));
        
        Observation observation3 = (Observation) new Observation().setId("Observation/3");
        observation3.setSubject(new ResourceReferenceDt(patient1));

        kSession.insert(patient1);
        kSession.insert(observation1);
        kSession.insert(observation2);
        kSession.insert(observation3);
        

        //Another Patient with 3 Observations
        Patient patient2 = (Patient) new Patient().setId("Patient/2");
        
        Observation observation4 = (Observation) new Observation().setId("Observation/4");
        observation4.setSubject(new ResourceReferenceDt(patient2));
        
        Observation observation5 = (Observation) new Observation().setId("Observation/5");
        observation5.setSubject(new ResourceReferenceDt(patient2));
        
        Observation observation6 = (Observation) new Observation().setId("Observation/6");
        observation6.setSubject(new ResourceReferenceDt(patient2));

        kSession.insert(patient2);
        kSession.insert(observation4);
        kSession.insert(observation5);
        kSession.insert(observation6);

        Assert.assertEquals(6, kSession.fireAllRules());
        System.out.println(" ---- Finished test2PatientsWith3ObservationsChecked() Test ---");
        kSession.dispose();
    }
   
}
