/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class SingletonRulesJUnitTest {

    public SingletonRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {
//        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
//            .importRuntimeDependencies().resolve().withTransitivity().asFile();

        JavaArchive war = ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.drools.workshop")
                .addPackages(true, "org.apache.deltaspike")
                .addAsServiceProvider(BusinessScopeExtension.class, BusinessScopeContext.class)
                //                .addAsLibraries(libs)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    
    @Inject
    private MyApplicationScopedBean myApplicationBean;
    
    @Inject
    private MyRequestScopedBean myRequestBean;

    @Test
    @InSequence(0)
    @Ignore
    public void helloRequestScoped() {
        Assert.assertNotNull(myRequestBean);
        ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);


        ctxCtrl.startContext(RequestScoped.class);


        int myBeanHashcode = myRequestBean.getMyBean().hashCode();
        int myKieSessionHashcode = myRequestBean.getkSession().hashCode();

        int result = myRequestBean.doSomething("hello 0");
        Assert.assertEquals(1, result);

        ctxCtrl.stopContext(RequestScoped.class);
        ctxCtrl.startContext(RequestScoped.class);
        

        Assert.assertNotEquals(myBeanHashcode, myRequestBean.getMyBean().hashCode());
        Assert.assertNotEquals(myKieSessionHashcode, myRequestBean.getkSession().hashCode());


        myBeanHashcode = myRequestBean.getMyBean().hashCode();
        myKieSessionHashcode = myRequestBean.getkSession().hashCode();

        result = myRequestBean.doSomething("hello 1");
        Assert.assertEquals(1, result);
        ctxCtrl.stopContext(RequestScoped.class);
        ctxCtrl.startContext(RequestScoped.class);

        
        Assert.assertNotEquals(myBeanHashcode, myRequestBean.getMyBean().hashCode());
        Assert.assertNotEquals(myKieSessionHashcode, myRequestBean.getkSession().hashCode());

        myBeanHashcode = myRequestBean.getMyBean().hashCode();
        myKieSessionHashcode = myRequestBean.getkSession().hashCode();

        result = myRequestBean.doSomething("hello 2");
        Assert.assertEquals(1, result);
       
        myBeanHashcode = myRequestBean.getMyBean().hashCode();
        myKieSessionHashcode = myRequestBean.getkSession().hashCode();
        
        ctxCtrl.stopContext(RequestScoped.class);
        ctxCtrl.startContext(RequestScoped.class);

        Assert.assertNotEquals(myBeanHashcode, myRequestBean.getMyBean().hashCode());
        Assert.assertNotEquals(myKieSessionHashcode, myRequestBean.getkSession().hashCode());

    }

    @Test
    @InSequence(1)
    public void helloApplicationScoped() {
        
    }
    
}
