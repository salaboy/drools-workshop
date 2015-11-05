/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.endpoint.api;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.drools.workshop.cepmodel.FraudSuspicion;
import org.drools.workshop.cepmodel.Transaction;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author salaboy
 */
@Path("transaction")
public interface TransactionEventService {
    
    @POST
    public void addTransaction(@NotNull Transaction event) throws BusinessException;
    
    @POST
    @Path("/blacklist/{id}")
    public void blacklist(@NotNull @NotEmpty @PathParam("id") String name) throws BusinessException;
    
    @GET 
    @Produces("application/json")
    @Path("/frauds")
    public List<FraudSuspicion> getFraudSuspicions() throws BusinessException;
    
}
