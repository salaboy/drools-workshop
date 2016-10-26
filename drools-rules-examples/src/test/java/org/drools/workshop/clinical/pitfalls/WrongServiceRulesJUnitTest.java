/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.pitfalls;

import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.clinical.services.MyConditionsProviderServiceImpl;
import org.drools.workshop.misc.services.SlowMyServiceImpl;

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
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class WrongServiceRulesJUnitTest {

    public WrongServiceRulesJUnitTest() {
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
    @KBase("clinical-wrongServiceKBase")
    private KieBase kBase;

    @Test
    public void testSlowServiceInAGlobal() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();

        kSession.setGlobal("myService", new SlowMyServiceImpl());
        System.out.println(" ---- Starting testSlowServiceInAGlobal() Test ---");

        List<Observation> observations = generateObservations(20);
        for (Observation o : observations) {
            kSession.insert(o);
        }

        Assert.assertEquals(20, kSession.fireAllRules());
        System.out.println(" ---- Finished testSlowServiceInAGlobal() Test ---");
        kSession.dispose();
    }

    @Test
    public void testServiceCallInRHS() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();

        kSession.setGlobal("myConditionsProviderService", new MyConditionsProviderServiceImpl());
        System.out.println(" ---- Starting testServiceCallInRHS() Test ---");

        Patient patient = (Patient) new Patient().setId("Patient/1");
        FactHandle patientHandle = kSession.insert(patient);

        Assert.assertEquals(50, kSession.fireAllRules());
        
        //A modification of the patient will execute the service 
        patient.addName(new HumanNameDt().addFamily("Richards"));
        kSession.update(patientHandle, patient);
        Assert.assertEquals(50, kSession.fireAllRules());
        
        System.out.println(" ---- Finished testServiceCallInRHS() Test ---");
        kSession.dispose();
    }

    private List<Observation> generateObservations(int amount) {
        List<Observation> results = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            results.add((Observation) new Observation().setId("Observation/"+(i+1)));
        }
        return results;
    }
}
