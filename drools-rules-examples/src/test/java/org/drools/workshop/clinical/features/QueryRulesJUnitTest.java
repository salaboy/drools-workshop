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
import java.util.Random;
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
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.Variable;
import org.kie.api.runtime.rule.ViewChangedEventListener;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class QueryRulesJUnitTest {

    private Random random = new Random(10);

    public QueryRulesJUnitTest() {
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
    @KBase("clinical-queryKBase")
    private KieBase kBase;

    @Test
    public void testQuery() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testQuery() Test ---");

        Patient patient = (Patient) new Patient()
            .setId("Patient/1");
        List<Condition> conditions = createSnomedConditions(100, patient);
        for (Condition c : conditions) {
            kSession.insert(c);
        }
        
        kSession.fireAllRules();
        QueryResults asthmaConditions = kSession.getQueryResults("all conditions for Patient", patient, "http://snomed.info/sct", "195967001");
        for (QueryResultsRow row : asthmaConditions) {
            Condition c = (Condition) row.get("$c");
            System.out.println(" >>> Result : " + c +", Code: "+c.getCode().getCodingFirstRep().getSystem()+"/"+c.getCode().getCodingFirstRep().getCode());
        }

        System.out.println(" ---- Finished testQuery() Test ---");
        kSession.dispose();
    }
    
    @Test
    public void testWithOptionalQuery() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testWithOptionalQuery() Test ---");

        Patient patient = (Patient) new Patient()
            .setId("Patient/1");
        List<Condition> conditions = createSnomedConditions(100, patient);
        for (Condition c : conditions) {
            kSession.insert(c);
        }
        
        kSession.fireAllRules();
        QueryResults asthmaConditions = kSession.getQueryResults("all conditions for Patient", patient, Variable.v, Variable.v);
        for (QueryResultsRow row : asthmaConditions) {
            Condition c = (Condition) row.get("$c");
            System.out.println(" >>> Result : " + c +", Code: "+c.getCode().getCodingFirstRep().getSystem()+"/"+c.getCode().getCodingFirstRep().getCode());
        }

        System.out.println(" ---- Finished testWithOptionalQuery() Test ---");
        kSession.dispose();
    }
    

    @Test
    public void testLiveQuery() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testLiveQuery() Test ---");
        
        Patient patient = (Patient) new Patient()
            .setId("Patient/1");
        List<Condition> conditions = createSnomedConditions(10, patient);
        
        kSession.openLiveQuery("all conditions for Patient", new Object[]{patient, "http://snomed.info/sct", "195967001"}, new ViewChangedEventListener() {

            public void rowInserted(Row row) {
                Condition c = (Condition) row.get("$c");
                System.out.println(" >> Condition Inserted: " + c +", Code: "+c.getCode().getCodingFirstRep().getSystem()+"/"+c.getCode().getCodingFirstRep().getCode());
            }

            public void rowDeleted(Row row) {
                Condition c = (Condition) row.get("$c");
                System.out.println(" >> Condition Deleted: " + c +", Code: "+c.getCode().getCodingFirstRep().getSystem()+"/"+c.getCode().getCodingFirstRep().getCode());
            }

            public void rowUpdated(Row row) {
                Condition c = (Condition) row.get("$c");
                System.out.println(" >> Condition Updated: " + c +", Code: "+c.getCode().getCodingFirstRep().getSystem()+"/"+c.getCode().getCodingFirstRep().getCode());
            }
        });

        for (Condition c : conditions) {
            kSession.insert(c);
            kSession.fireAllRules();
        }

        System.out.println(" ---- Finished testLiveQuery() Test ---");
        kSession.dispose();
    }

    private List<Condition> createSnomedConditions(int number, Patient patient) {

        List<Condition> conditions = new ArrayList<>();
        for (int i = 0; i < number; i++) {

            String code = null;
            switch (random.nextInt(3)) {
                case 0:
                    code = "195967001"; //Asthma (disorder)
                    break;
                case 1:
                    code = "73211009"; //Diabetes mellitus (disorder)
                    break;
                case 2:
                    code = "8098009"; //Sexually transmitted infectious disease (disorder)
                    break;
            }

            conditions.add((Condition) new Condition()
                .setCode(new CodeableConceptDt("http://snomed.info/sct", code))
                .setClinicalStatus(ConditionClinicalStatusCodesEnum.ACTIVE)
                .setVerificationStatus(ConditionVerificationStatusEnum.CONFIRMED)
                .setPatient(new ResourceReferenceDt(patient))
                .setId("Condition/"+(i+1)));
        }

        return conditions;
    }

}
