/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.services;

import ca.uhn.fhir.model.dstu2.resource.Condition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esteban
 */
public class MyConditionsProviderServiceImpl implements MyConditionsProviderService {

    @Override
    public List<Condition> getConditions() {
        System.out.println(">>> Loading Conditions...");
        return generateConditions(50);
    }
    
    private List<Condition> generateConditions(int amount) {
        List<Condition> results = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            results.add((Condition) new Condition().setId("Condition/"+(i+1)));
        }
        return results;
    }
}
