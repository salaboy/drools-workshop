/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.endpoint.api;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.model.Item;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author salaboy
 */
@Path("warehouse")
public interface WarehouseItemsService {

    @GET
    @Produces("application/json")
    @Path("/items/")
    public List<Item> getAllItems() throws BusinessException;

    @POST
    @Path("/items/")
    public void newItem(@NotNull Item item) throws BusinessException;
    
    @POST
    @Path("/items/init")
    public void init() throws BusinessException;

    @DELETE
    @Path("/items/{id}")
    public void removeItem(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
    
    @GET
    @Path("/items/{id}")
    public Item getItem(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;

}
