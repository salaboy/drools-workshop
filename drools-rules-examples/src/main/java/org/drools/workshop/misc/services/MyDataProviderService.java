/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.misc.services;

import java.util.List;
import org.drools.workshop.misc.model.Person;

/**
 *
 * @author salaboy
 */
public interface MyDataProviderService {
   public List<Person> getPersons();
}
