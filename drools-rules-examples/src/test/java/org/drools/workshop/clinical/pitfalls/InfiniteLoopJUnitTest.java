/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.pitfalls;

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.resource.RiskAssessment;
import ca.uhn.fhir.model.primitive.IntegerDt;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class InfiniteLoopJUnitTest {

    public InfiniteLoopJUnitTest() {
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
    @KSession("clinical-infiniteLoopKSession")
    private KieSession kSession;

    @Test
    public void testUpdateLoop() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testUpdateLoop() Test ---");

        Patient patient = (Patient) new Patient().setId("Patient/1");
        
        RiskAssessment riskAssessment = (RiskAssessment) new RiskAssessment().setId("RiskAssessment/1");
        
        Observation smokerObservation = (Observation) new Observation()
            .setCode(new CodeableConceptDt("http://snomed.info/sct", "428041000124106"))
            .setId("Observation/1");
        
        Observation bodyMassIndexObservation = (Observation) new Observation()
            .setCode(new CodeableConceptDt("http://snomed.info/sct", "60621009"))
            .setValue(new IntegerDt(45))
            .setId("Observation/2");

        kSession.insert(patient);
        kSession.insert(riskAssessment);
        kSession.insert(smokerObservation);
        kSession.insert(bodyMassIndexObservation);
        
        Assert.assertEquals(15, kSession.fireAllRules(15));
        System.out.println(" ---- Finished testUpdateLoop() Test ---");
        
    }

    

}
