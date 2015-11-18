/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.shopping;

import org.drools.workshop.endpoint.api.ShoppingCartService;
import org.drools.workshop.endpoint.config.AuthRESTResponseFilter;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.endpoint.exception.HttpStatusExceptionHandler;
import org.drools.workshop.endpoint.impl.ShoppingCartServiceImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 *
 * @author salaboy
 */
public class App {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        container.start();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.setContextRoot("/api");
        deployment.addPackage("org.drools.workshop.model");
        deployment.addPackages(true, "org.kie.api");
        deployment.addPackages(true, "org.drools");
        deployment.addResource(ShoppingCartService.class);
        deployment.addResource(ShoppingCartServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);
        deployment.addClass(AuthRESTResponseFilter.class);
        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
