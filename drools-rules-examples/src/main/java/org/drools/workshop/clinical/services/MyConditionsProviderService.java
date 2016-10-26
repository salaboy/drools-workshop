/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.services;

import ca.uhn.fhir.model.dstu2.resource.Condition;
import java.util.List;

/**
 *
 * @author esteban
 */
public interface MyConditionsProviderService {
   public List<Condition> getConditions();
}
