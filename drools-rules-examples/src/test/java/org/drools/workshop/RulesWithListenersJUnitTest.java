/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import javax.inject.Inject;
import org.drools.workshop.model.Person;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KSession;
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
    @KSession("rules01KSession")
    private KieSession kSession;

    @Test
    @Ignore
    public void testRulesWithListeners() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testPersonRulesWithJustAPerson() Test ---");
        kSession.addEventListener(new AgendaEventListener() {

            public void matchCreated(MatchCreatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void matchCancelled(MatchCancelledEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void afterMatchFired(AfterMatchFiredEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        kSession.addEventListener(new RuleRuntimeEventListener() {

            public void objectInserted(ObjectInsertedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        kSession.insert(new Person());

        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testPersonRulesWithJustAPerson() Test ---");
    }


}
