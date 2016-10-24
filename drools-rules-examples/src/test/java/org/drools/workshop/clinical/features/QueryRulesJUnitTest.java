/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.features;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.drools.workshop.misc.model.Person;
import org.drools.workshop.misc.model.Person.Gender;

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
import org.kie.api.runtime.rule.ViewChangedEventListener;

/**
 *
 * @author salaboy
 */
@RunWith(Arquillian.class)
public class QueryRulesJUnitTest {

    public QueryRulesJUnitTest() {
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
    @KBase("clinical-queryKBase")
    private KieBase kBase;

    @Test
    public void testQuery() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testQuery() Test ---");

        List<Person> generatePersons = generatePersons(100);
        for (Person p : generatePersons) {
            kSession.insert(p);
        }
        kSession.fireAllRules();
        QueryResults queryResults = kSession.getQueryResults("all persons older than", 50);
        for (QueryResultsRow row : queryResults) {
                System.out.println(" >>> Result : " + row.get("$p"));
        }

        System.out.println(" ---- Finished testQuery() Test ---");
        kSession.dispose();
    }
    
    
    @Test
    public void testLiveQuery() {
        Assert.assertNotNull(kBase);
        KieSession kSession = kBase.newKieSession();
        System.out.println(" ---- Starting testLiveQuery() Test ---");
        kSession.openLiveQuery("all persons older than", new Object[]{50}, new ViewChangedEventListener() {

            public void rowInserted(Row row) {
                System.out.println(" >> Row Inserted: "+row);
            }

            public void rowDeleted(Row row) {
                System.out.println(" >> Row Deleted: "+row);
            }

            public void rowUpdated(Row row) {
                System.out.println(" >> Row Updated: "+row);
            }
        });
        
        List<Person> generatePersons = generatePersons(100);
        for (Person p : generatePersons) {
            kSession.insert(p);
            kSession.fireAllRules();
            
        }
       

        System.out.println(" ---- Finished testLiveQuery() Test ---");
        kSession.dispose();
    }

    private List<Person> generatePersons(int amount) {
        List<Person> results = new ArrayList<Person>();
        for (int i = 0; i < amount; i++) {
            results.add(new Person("name " + i, (int) Math.round(Math.random() * 100) % 100, "name" + i + "@mail.com", "Somewhere", ((i % 2) == 1) ? Gender.FEMALE : Gender.MALE));
        }
        return results;
    }

}
