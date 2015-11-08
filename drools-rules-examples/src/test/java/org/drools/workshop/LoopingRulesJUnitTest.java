/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import javax.inject.Inject;
import org.drools.workshop.model.Person;
import org.drools.workshop.model.Student;

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
public class LoopingRulesJUnitTest {

    public LoopingRulesJUnitTest() {
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
    @KSession("rules02KSession")
    private KieSession kSession;

    @Test
    public void testUpdateLoop() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testUpdateLoop() Test ---");

        kSession.insert(new Person("salaboy", 32, "salaboy@mail.com", "London", Person.Gender.MALE));

        Assert.assertEquals(15, kSession.fireAllRules(15));
        System.out.println(" ---- Finished testUpdateLoop() Test ---");
    }



}
