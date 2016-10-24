/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.misc.services;

/**
 *
 * @author salaboy
 */
public class MyServiceImpl implements MyService{

    public void notify(String message) {
        System.out.println(">>> Notify Message: "+ message);
    }
    
}
