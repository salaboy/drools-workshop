/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salaboy
 */
public class University {

    private String name;
    private List<Person> enrolled = new ArrayList<Person>();

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(List<Person> enrolled) {
        this.enrolled = enrolled;
    }

}
