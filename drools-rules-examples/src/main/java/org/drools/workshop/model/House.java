/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.model;

import java.util.List;

/**
 *
 * @author salaboy
 */
public class House {
    private List<Room> rooms;
    
    public House(List<Room> rooms, List<Person> persons) {
        this.rooms = rooms;
    }

    public House() {
    }
    

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

   

    @Override
    public String toString() {
        return "House{" + "rooms=" + rooms + '}';
    }

    
    
    
    
           
}
