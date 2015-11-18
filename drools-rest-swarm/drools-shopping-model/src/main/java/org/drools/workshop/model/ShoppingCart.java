/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author salaboy
 */
@XmlRootElement
public class ShoppingCart {

    private String id;
    private int numberOfItems;
    private String userId;

    public ShoppingCart() {
    }

    public ShoppingCart(String id) {
        this.id = id;
    }

    public ShoppingCart(String id, int numberOfItems) {
        this(id);
        this.numberOfItems = numberOfItems;
    }

    public ShoppingCart(String id, int numberOfItems, String userId) {
        this(id, numberOfItems);
        this.userId = userId;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "id=" + id + ", numberOfItems=" + numberOfItems + ", userId=" + userId + '}';
    }
    
    
    
}
