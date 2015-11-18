/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.endpoint.config;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author salaboy
 */
@Provider
public class AuthRESTResponseFilter implements ContainerResponseFilter {

    private final static Logger log = Logger.getLogger(AuthRESTResponseFilter.class.getName());

    @Override

    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

//        log.info("Filtering REST Response");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");

        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        responseCtx.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

    }

}
