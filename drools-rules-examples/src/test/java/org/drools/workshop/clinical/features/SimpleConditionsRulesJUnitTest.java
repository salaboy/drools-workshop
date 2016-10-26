/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.primitive.DateDt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import org.drools.workshop.clinical.model.AsthmaticPatient;

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
public class SimpleConditionsRulesJUnitTest {

    public SimpleConditionsRulesJUnitTest() {
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
    @KSession("clinical-simpleConditionsKSession")
    private KieSession kSession;

    @Test
    public void testPatientRulesWithJustAPatient() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testPatientRulesWithJustAPatient() Test ---");

        kSession.insert(new Patient().setId("Patient/1"));
        
        Assert.assertEquals(1, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientRulesWithJustAPatient() Test ---");
    }

    @Test
    public void testPatientRulesWithAPatientFrom82() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testPatientRulesWithAnPatientFrom82() Test ---");
        kSession.insert(new Patient()
            .setBirthDateWithDayPrecision(parseDate("1982-01-01"))
            .setId("Patient/1")
        );

        Assert.assertEquals(2, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientRulesWithAnPatientFrom82() Test ---");
    }

    @Test
    public void testPatientRulesWithAPatientFromLondonFrom82() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testPatientRulesWithAPatientFromLondonFrom82() Test ---");
        kSession.insert(new Patient()
            .setBirthDateWithDayPrecision(parseDate("1982-01-01"))
            .setGender(AdministrativeGenderEnum.MALE)
            .addAddress(new AddressDt().setCity("London"))
            .setId("Patient/1")
        );

        Assert.assertEquals(4, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientRulesWithAPatientFromLondonFrom82() Test ---");
    }

    @Test
    public void testPatientRulesInheritanceInModel() {
        Assert.assertNotNull(kSession);
        System.out.println(" ---- Starting testPatientRulesInheritanceInModel() Test ---");
        
        kSession.insert(new AsthmaticPatient()
            .setDiagnosedDate(new DateDt(parseDate("2014-06-07")))
            .setBirthDateWithDayPrecision(parseDate("1982-01-01"))
            .setGender(AdministrativeGenderEnum.MALE)
            .addAddress(new AddressDt().setCity("London"))
            .setId("Patient/1")
        );

        Assert.assertEquals(5, kSession.fireAllRules());
        System.out.println(" ---- Finished testPatientRulesInheritanceInModel() Test ---");
    }
    
    private Date parseDate(String date){
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
