/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class MyApplicationScopedBean {

    public MyApplicationScopedBean() {

    }

    public void doSomething(String text) {
        System.out.println(" >>> App Doing something with: " + text);
    }

}
