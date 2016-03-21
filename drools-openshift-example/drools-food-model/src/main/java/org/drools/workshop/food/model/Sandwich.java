/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.model;

import org.drools.workshop.food.model.ingredients.WhiteBread;

/**
 *
 * @author salaboy
 */
public class Sandwich extends Meal {

    public Sandwich() {
        ingredients.add(new WhiteBread());
    }

    public Sandwich(WhiteBread b) {
        ingredients.add(new WhiteBread());
    }

}
