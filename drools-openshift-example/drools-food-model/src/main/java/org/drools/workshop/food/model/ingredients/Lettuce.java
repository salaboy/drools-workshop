/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.model.ingredients;

import org.drools.workshop.food.model.Ingredient;

/**
 *
 * @author salaboy
 */
public class Lettuce implements Ingredient {

    private final String name = "Lettuce";
    private final int calories = 10;

    public Lettuce() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCalories() {
        return calories;
    }

}
