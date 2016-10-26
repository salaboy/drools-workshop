/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.resource.Location;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IntegerDt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.drools.workshop.clinical.model.FixedCapacityLocation;

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
                .addPackages(true, "org.drools.workshop.clinical.model")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    @KBase("clinical-accumulationKBase")
    private KieBase kBase;

    private final Random random = new Random(10);
    
    @Test
    public void test20PatientsInALocation() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting test20PatientsInALocation() Test ---");
        
        Location location = (Location) new FixedCapacityLocation()
            .setCapacity(15)
            .setId("Location/1");
        
        List<Patient> patients = generatePatients(20);
        for(Patient p : patients){
            kSession.insert(p);
        }
        kSession.insert(location);

        Assert.assertEquals(1, kSession.fireAllRules());
        
        System.out.println(" ---- Finished test20PatientsInALocation() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void testPatientWithBloodPressureObservations() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testPatientWithBloodPressureObservations() Test ---");
        
        
        kSession.insert(generatePatients(1).get(0));
        
        List<Observation> observations = generateBloodPressureObservations(60);
        for (Observation observation : observations) {
            kSession.insert(observation);
        }

        Assert.assertEquals(1, kSession.fireAllRules());
        
        System.out.println(" ---- Finished testPatientWithBloodPressureObservations() Test ---");
        kSession.dispose();
    }
   
    
    private List<Patient> generatePatients(int amount) {
        List<Patient> results = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            results.add((Patient) new Patient()
                .setId("Patient/"+(i+1)));
        }
        return results;
    }
    
    private List<Observation> generateBloodPressureObservations(int amount) {
        List<Observation> results = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            results.add((Observation) new Observation()
                .setCode(new CodeableConceptDt("http://loinc.org", "55284-4")) //Diastolic
                .setValue(new IntegerDt(random.nextInt(40)+60))
                .setId("Observation/"+(i+1)));
        }
        return results;
    }

}
