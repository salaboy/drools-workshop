/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Condition;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.ConditionClinicalStatusCodesEnum;
import ca.uhn.fhir.model.dstu2.valueset.ConditionVerificationStatusEnum;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;
import org.drools.workshop.misc.model.Room;
import org.drools.workshop.misc.services.MyServiceImpl;

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
public class UsingGlobalsRulesJUnitTest {

    public UsingGlobalsRulesJUnitTest() {
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
    @KBase("clinical-usingGlobalsKBase")
    private KieBase kBase;

    @Test
    public void testUsingGlobalToTrackRulesFiring() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        List<String> rulesFired = new ArrayList<>();
        kSession.setGlobal("rulesFired", rulesFired);
        System.out.println(" ---- Starting testUsingGlobalToTrackRulesFiring() Test ---");

        Patient patient = (Patient) new Patient()
            .setId("Patient/1");
        Condition condition = createDiabetesCondition(patient, "Condition/1");
        kSession.insert(patient);
        kSession.insert(condition);

        Assert.assertEquals(1, kSession.fireAllRules());
        Assert.assertTrue(rulesFired.contains("Identify Diabetic Patients"));
        
        System.out.println(" ---- Finished testUsingGlobalToTrackRulesFiring() Test ---");
        kSession.dispose();
    }

    @Test
    public void testServiceInAGlobal() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();

        kSession.setGlobal("myService", new MyServiceImpl());
        System.out.println(" ---- Starting testServiceInAGlobal() Test ---");
        
        Patient patient = (Patient) new Patient()
            .setId("Patient/1");
        Condition condition = createAsthmaCondition(patient, "Condition/1");
        kSession.insert(patient);
        kSession.insert(condition);

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testServiceInAGlobal() Test ---");
        kSession.dispose();
    }

    private Condition createAsthmaCondition(Patient patient, String id) {
        return (Condition) new Condition()
            .setCode(new CodeableConceptDt("http://snomed.info/sct", "195967001")) //Asthma (disorder)
            .setClinicalStatus(ConditionClinicalStatusCodesEnum.ACTIVE)
            .setVerificationStatus(ConditionVerificationStatusEnum.CONFIRMED)
            .setPatient(new ResourceReferenceDt(patient))
            .setId(id);
    }

    private Condition createDiabetesCondition(Patient patient, String id) {
        return (Condition) new Condition()
            .setCode(new CodeableConceptDt("http://snomed.info/sct", "73211009")) //Diabetes mellitus (disorder)
            .setClinicalStatus(ConditionClinicalStatusCodesEnum.ACTIVE)
            .setVerificationStatus(ConditionVerificationStatusEnum.CONFIRMED)
            .setPatient(new ResourceReferenceDt(patient))
            .setId(id);
    }
}
