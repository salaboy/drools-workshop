/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.cep;

import org.drools.workshop.endpoint.api.TransactionEventService;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.drools.workshop.endpoint.exception.HttpStatusExceptionHandler;
import org.drools.workshop.endpoint.impl.TransactionEventServiceImpl;
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
        deployment.setContextRoot("/cep");
        deployment.addResource(TransactionEventService.class);
        deployment.addResource(TransactionEventServiceImpl.class);
        deployment.addClass(HttpStatusExceptionHandler.class);
        deployment.addClass(BusinessException.class);
        deployment.addAllDependencies();
        container.deploy(deployment);
    }
}
