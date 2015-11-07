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
    private List<Person> persons;
    
    public House(List<Room> rooms, List<Person> persons) {
        this.rooms = rooms;
        this.persons = persons;
    }

    public House() {
    }
    

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "House{" + "rooms=" + rooms + ", persons=" + persons + '}';
    }

    
    
    
    
           
}
