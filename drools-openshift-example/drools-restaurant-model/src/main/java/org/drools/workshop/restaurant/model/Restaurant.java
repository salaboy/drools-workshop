/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.restaurant.model;

import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class Restaurant {

    private String id;
    private String name;
    private double lon;
    private double lat;
    
    public Restaurant() {
    }

    public Restaurant(String name, double lon, double lat) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
