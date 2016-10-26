/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class RulesWithListenersJUnitTest {

    public RulesWithListenersJUnitTest() {
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
    @KBase("clinical-simpleConditionsKBase")
    private KieBase kBase;

    @Test
    public void testRulesWithListeners() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testRulesWithListeners() Test ---");
        kSession.addEventListener(new AgendaEventListener() {

            @Override
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println(">>> Match Created: " + event);
            }

            @Override
            public void matchCancelled(MatchCancelledEvent event) {

            }

            @Override
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println(">>> Before Match Fired: " + event);
            }

            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println(">>> After Match Fired: " + event);
            }

            @Override
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

            }

            @Override
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {

            }

            @Override
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            @Override
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            @Override
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }

            @Override
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }
        });
        kSession.addEventListener(new RuleRuntimeEventListener() {

            @Override
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println(">>> Object Inserted: " + event);
            }

            @Override
            public void objectUpdated(ObjectUpdatedEvent event) {

            }

            @Override
            public void objectDeleted(ObjectDeletedEvent event) {

            }
        });

        kSession.insert(new Patient()
            .setBirthDateWithDayPrecision(parseDate("1982-01-01"))
            .setGender(AdministrativeGenderEnum.MALE)
            .addAddress(new AddressDt().setCity("London"))
            .setId("Patient/1")
        );

        Assert.assertEquals(4, kSession.fireAllRules());
        System.out.println(" ---- Finished testRulesWithListeners() Test ---");
    }

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
