/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.delivery.model;

import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;
import org.drools.workshop.restaurant.model.Restaurant;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class Delivery {

    private String id;
    
    private double currentLon;
    private double currentLat;
    private Restaurant restaurant;
    private double goalLon;
    private double goalLat;
    
    public Delivery() {
        this.id = UUID.randomUUID().toString();
    }

    public Delivery(Restaurant restaurant, double goalLon, double goalLat) {
        this();
        this.restaurant = restaurant;
        this.goalLon = goalLon;
        this.goalLat = goalLat;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public double getGoalLon() {
        return goalLon;
    }

    public void setGoalLon(double goalLon) {
        this.goalLon = goalLon;
    }

    public double getGoalLat() {
        return goalLat;
    }

    public void setGoalLat(double goalLat) {
        this.goalLat = goalLat;
    }

    
    

}
