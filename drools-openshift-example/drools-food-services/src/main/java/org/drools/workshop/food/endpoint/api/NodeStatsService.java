/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.food.endpoint.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.drools.workshop.food.endpoint.exception.BusinessException;
import org.drools.workshop.food.model.Stats;

/**
 *
 * @author salaboy
 */
@Path("node")
public interface NodeStatsService {

    @GET
    @Path("")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Stats getStats() throws BusinessException;

}
