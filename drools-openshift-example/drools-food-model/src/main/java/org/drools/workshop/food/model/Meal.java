/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salaboy
 */
public class Meal {

    protected List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Meal() {
    }

    public Meal(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
    }

    public int getTotalCalories() {
        int cals = 0;
        if (this.ingredients != null) {
            for (Ingredient i : this.ingredients) {
                cals += i.getCalories();
            }
        }
        return cals;
    }
}
