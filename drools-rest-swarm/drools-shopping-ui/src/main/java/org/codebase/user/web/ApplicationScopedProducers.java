/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.codebase.user.web;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class ApplicationScopedProducers {

    @PersistenceContext(name = "primary")
    private EntityManager em;

    @Produces
    public EntityManager produceEntityManager() {
        return em;
    }
}
