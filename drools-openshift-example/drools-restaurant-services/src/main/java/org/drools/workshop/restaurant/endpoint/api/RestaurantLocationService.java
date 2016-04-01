/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.restaurant.endpoint.api;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.drools.workshop.restaurant.model.Restaurant;
import org.drools.workshop.restaurant.endpoint.exception.BusinessException;

/**
 *
 * @author salaboy
 */
@Path("restaurant")
public interface RestaurantLocationService {

    @GET
    @Path("")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Restaurant> findRestaurantsNearMe(@QueryParam("lon") double lon, @QueryParam("lat") double lat) throws BusinessException;

}
