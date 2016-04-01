/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class BusinessScopedRulesJUnitTest {

    public BusinessScopedRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.drools.workshop")
                .addAsServiceProvider(BusinessScopeExtension.class, BusinessScopeContext.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    private BusinessScopedRules bsr;
    

    @Test
    public void hello() {
        Assert.assertNotNull(bsr);
        bsr.getkSession().insert("Hi There From Test 1!");
        Assert.assertEquals(1, bsr.getkSession().fireAllRules());
    }
    
    @Test
    public void hello2() {
        Assert.assertNotNull(bsr);
        bsr.getkSession().insert("Hi There From Test 2!");
        Assert.assertEquals(1, bsr.getkSession().fireAllRules());
    }
}
