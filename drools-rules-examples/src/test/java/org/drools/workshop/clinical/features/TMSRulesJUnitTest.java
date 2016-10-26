/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
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
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class TMSRulesJUnitTest {

    public TMSRulesJUnitTest() {
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
    @KBase("clinical-tmsKBase")
    private KieBase kBase;

    @Test
    public void testSimpleTMS() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testSimpleTMS() Test ---");
        
        kSession.addEventListener(new RuleRuntimeEventListener() {

            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println(" >>> Fact Inserted" + event );
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println(" >>> Fact Updated" + event );
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println(" >>> Fact Deleted" + event );
            }
        });
        
        
        Patient patient = (Patient) new Patient()
            .addAddress(new AddressDt().setCity("London"))
            .setId("Patient/1");
        
        FactHandle patientFH = kSession.insert(patient);
        kSession.fireAllRules();
        
        patient.getAddressFirstRep().setCity("Buenos Aires");
        kSession.update(patientFH, patient);
        kSession.fireAllRules();

        System.out.println(" ---- Finished testSimpleTMS() Test ---");
        kSession.dispose();
    }

}
