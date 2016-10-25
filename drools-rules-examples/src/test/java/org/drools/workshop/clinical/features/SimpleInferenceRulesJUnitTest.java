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
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.ConditionClinicalStatusCodesEnum;
import ca.uhn.fhir.model.dstu2.valueset.ConditionVerificationStatusEnum;
import javax.inject.Inject;

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
    
    private enum Severity {
        MILD("255604002"),
        MODERATE("6736007"),
        SEVERE("24484000");
        
        private String code;

        private Severity(String code) {
            this.code = code;
        }
        
    }

    public SimpleInferenceRulesJUnitTest() {
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
    @KBase("clinical-simpleInferenceKBase")
    private KieBase kBase;

    @Test
    public void testFemaleSevereAsthmaticPatient() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testFemaleSevereAsthmaticPatient() Test ---");
        
        //Female, Mild Asthmatic Patient
        Patient patient1 = (Patient) new Patient()
            .setGender(AdministrativeGenderEnum.FEMALE)
            .setId("Patient/1");
        Condition condition1 = createAsthmaCondition(patient1, "Condition/1", Severity.MILD);
        kSession.insert(patient1);
        kSession.insert(condition1);
        
        //Male, Severe Asthmatic Patient
        Patient patient2 = (Patient) new Patient()
            .setGender(AdministrativeGenderEnum.MALE)
            .setId("Patient/2");
        Condition condition2 = createAsthmaCondition(patient2, "Condition/2", Severity.SEVERE);
        kSession.insert(patient2);
        kSession.insert(condition2);
        
        //Female, Severe Asthmatic Patient
        Patient patient3 = (Patient) new Patient()
            .setGender(AdministrativeGenderEnum.FEMALE)
            .setId("Patient/3");
        Condition condition3 = createAsthmaCondition(patient3, "Condition/3", Severity.SEVERE);
        kSession.insert(patient3);
        kSession.insert(condition3);
        
        
        
        Assert.assertEquals(3, kSession.fireAllRules());
        System.out.println(" ---- Finished testFemaleSevereAsthmaticPatient() Test ---");
        kSession.dispose();
    }

    
    private Condition createAsthmaCondition(Patient patient, String id, Severity severity ){
        return (Condition) new Condition()
            .setCode(new CodeableConceptDt("http://snomed.info/sct", "195967001"))
            .setClinicalStatus(ConditionClinicalStatusCodesEnum.ACTIVE)
            .setVerificationStatus(ConditionVerificationStatusEnum.CONFIRMED)
            .setSeverity(new CodeableConceptDt("http://snomed.info/sct", severity.code))
            .setPatient(new ResourceReferenceDt(patient))
            .setId(id);
    }
   
}
