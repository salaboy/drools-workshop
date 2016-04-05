/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.delivery.endpoint.api;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.drools.workshop.delivery.endpoint.exception.BusinessException;
import org.drools.workshop.delivery.model.Delivery;

/**
 *
 * @author salaboy
 */
@Path("delivery")
public interface DeliveryService {

    @POST
    @Path("")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public String newDelivery(@NotNull Delivery delivery) throws BusinessException;

    @GET
    @Path("{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Delivery getDelivery(@QueryParam("id") String deliveryId) throws BusinessException;
    
    @GET
    @Path("mock")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Delivery getMockDelivery() throws BusinessException;

    @GET
    @Path("")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<String> getAllDeliveriesIds() throws BusinessException;

}
