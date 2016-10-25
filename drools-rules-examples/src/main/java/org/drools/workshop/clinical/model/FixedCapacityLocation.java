/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.clinical.model;

import ca.uhn.fhir.model.dstu2.resource.Location;

/**
 *
 * @author esteban
 */
public class FixedCapacityLocation extends Location {
    
    private int capacity;

    public int getCapacity() {
        return capacity;
    }

    public FixedCapacityLocation setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }
    
}
