/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.misc.services;

import java.util.ArrayList;
import java.util.List;
import org.drools.workshop.misc.model.Person;

/**
 *
 * @author salaboy
 */
public class MyDataProviderServiceImpl implements MyDataProviderService {

    public List<Person> getPersons() {
        System.out.println(">>> Loading Persons ...");
        return generatePersons(50);
    }
    
    private List<Person> generatePersons(int amount) {
        List<Person> results = new ArrayList<Person>();
        for (int i = 0; i < amount; i++) {
            results.add(new Person("name " + i, (int) Math.round(Math.random()) % 100, "name" + i + "@mail.com", "Somewhere", ((i % 2) == 1) ? Person.Gender.FEMALE : Person.Gender.MALE));
        }
        return results;
    }
    
}
