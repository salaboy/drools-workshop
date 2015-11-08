/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.model;

/**
 *
 * @author salaboy
 */
public class Person {

    public enum Gender {
        MALE, FEMALE
    };

    private String name;
    private int age;
    private String email;
    private String city;
    private Gender gender;

    public Person() {
    }

    public Person(String name, int age, String email, String city, Gender gender) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.city = city;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + ", email=" + email + ", city=" + city + ", gender=" + gender + '}';
    }

}
