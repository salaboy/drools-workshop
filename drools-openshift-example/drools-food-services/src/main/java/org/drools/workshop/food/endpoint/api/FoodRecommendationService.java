/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.endpoint.api;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.drools.workshop.food.endpoint.exception.BusinessException;
import org.drools.workshop.food.model.Meal;
import org.drools.workshop.food.model.Person;

/**
 *
 * @author salaboy
 */
@Path("food")
public interface FoodRecommendationService {

    @POST
    @Path("")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Meal recommendMeal(@NotNull Person person) throws BusinessException;

}
