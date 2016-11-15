/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.demo.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.apache.poi.util.IOUtils;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.demo.model.Observation;
import org.drools.demo.model.Patient;

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
import org.drools.demo.model.recommendations.Recommendation;

/**
 *
 * @author salaboy
 */
@RunWith( Arquillian.class )
public class ClinicalRulesJUnitTest {

    public ClinicalRulesJUnitTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create( JavaArchive.class )
                .addPackages( true, "org.drools.demo.model" )
                .addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml" );
        //System.out.println(jar.toString(true));
        return jar;
    }

    @Inject
    @KBase()
    private KieBase kBase;

    private final Random random = new Random( 10 );

    @Test
    public void testPatientWithBloodPressureObservations() {

        printGeneratedDRL( ClinicalRulesJUnitTest.class.getResourceAsStream( "/rules/clinical-decision-table.xls" ), System.out );

        Assert.assertNotNull( kBase );
        KieSession kSession = kBase.newKieSession();

        kSession.insert( generatePatients( 1 ).get( 0 ) );

        List<Observation> observations = generateObservations( "headache", 4 );
        for ( Observation observation : observations ) {
            kSession.insert( observation );
        }

        Assert.assertEquals( 2, kSession.fireAllRules() );

        QueryResults queryResults = kSession.getQueryResults( "getAllRecommendations", ( Object ) null );
        for ( QueryResultsRow row : queryResults ) {
            Recommendation r = ( Recommendation ) row.get( "$r" );
            System.out.println( " >>> Result : " + r );
        }

        kSession.dispose();
    }

    private List<Patient> generatePatients( int amount ) {
        List<Patient> results = new ArrayList<>();
        for ( int i = 0; i < amount; i++ ) {
            results.add( new Patient( "salaboy", 33, "MALE" ) );
        }
        return results;
    }

    private List<Observation> generateObservations( String type, int amount ) {
        List<Observation> results = new ArrayList<>();
        for ( int i = 0; i < amount; i++ ) {
            results.add( new Observation( new Date(), type ) );
        }
        return results;
    }

    /**
     * Converts a decision table into DRL and prints the result in the
     * passed OutputStream.
     *
     * @param decisionTable the decision table to be converted.
     * @param target        the stream where the generated DRL will be printed.
     */
    private void printGeneratedDRL( InputStream decisionTable, OutputStream target ) {
        try {
            DecisionTableProviderImpl dtp = new DecisionTableProviderImpl();
            String drl = dtp.loadFromInputStream( decisionTable, null );

            IOUtils.copy( new ByteArrayInputStream( drl.getBytes() ), target );
        } catch ( IOException ex ) {
            throw new IllegalStateException( ex );
        }
    }

}
