/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.services;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salaboy
 */
public class SlowMyServiceImpl implements MyService{

    public void notify(String message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SlowMyServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">> My Service Message: "+ message);
    }
    
}
