/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.warehouse;

import org.drools.workshop.endpoint.api.WarehouseItemsService;
import org.drools.workshop.endpoint.config.AuthRESTResponseFilter;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.endpoint.exception.HttpStatusExceptionHandler;
import org.drools.workshop.endpoint.impl.WarehouseItemsServiceImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.keycloak.Secured;

/**
 *
 * @author salaboy
 */
public class App {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.as(Secured.class);
        deployment.setContextRoot("/api");
        deployment.addPackage("org.drools.workshop.model");
        deployment.addResource(WarehouseItemsService.class);
        deployment.addResource(WarehouseItemsServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);
        deployment.addClass(AuthRESTResponseFilter.class);
        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
