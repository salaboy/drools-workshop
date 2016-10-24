/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;

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
                .addPackages(true, "org.drools.workshop.model")
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

            public void matchCreated(MatchCreatedEvent event) {
                System.out.println(">>> Match Created: "+event);
            }

            public void matchCancelled(MatchCancelledEvent event) {
                
            }

            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println(">>> Before Match Fired: "+event);
            }

            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println(">>> After Match Fired: "+event);
            }

            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
                
            }

            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
                
            }

            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                
            }

            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                
            }

            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                
            }

            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                
            }
        });
        kSession.addEventListener(new RuleRuntimeEventListener() {

            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println(">>> Object Inserted: "+event);
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                
            }
        });
        
        kSession.insert(new Person());

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testRulesWithListeners() Test ---");
    }


}
