/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.model;

import org.drools.workshop.food.model.ingredients.Lettuce;

/**
 *
 * @author salaboy
 */
public class Salad extends Meal {

    public Salad() {
        ingredients.add(new Lettuce());
    }
    
    
    
}
