/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.endpoint.api;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("shopping")
public interface ShoppingCartService {
    @POST
    @Path("/new")
    public String newShoppingCart() throws BusinessException;
    
    @PUT
    @Path("/{id}")
    public void addItem(@PathParam("id") @NotEmpty @NotNull String id, @NotNull Item item) throws BusinessException;
    
    @DELETE
    @Path("/{id}/item")
    public void removeItem(@PathParam("id") @NotEmpty @NotNull String id, @NotNull Item item) throws BusinessException;
    
    @DELETE
    @Path("/{id}")
    public void removeShoppingCart(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
    
    @POST
    @Path("/{id}/checkout")
    public void checkout(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
    
    @POST
    @Path("/{id}/empty")
    public void empty(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
    
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public List<Item> getItems(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
    
    @GET
    @Produces("application/json")
    @Path("/carts")
    public Set<String> getShoppingCarts() throws BusinessException;
    
}
