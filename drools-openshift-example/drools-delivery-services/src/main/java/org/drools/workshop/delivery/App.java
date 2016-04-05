/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.delivery;


import java.util.List;
import java.util.Map;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.topology.Topology;
import org.wildfly.swarm.topology.TopologyArchive;
import org.wildfly.swarm.topology.TopologyListener;

/**
 *
 * @author salaboy
 */
public class App {

    public static void main(String[] args) throws Exception {
        Container container = new Container();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.as(TopologyArchive.class).advertise("deliveryService");
        deployment.setContextRoot("/api");
        deployment.addAsLibrary(container.createDefaultDeployment());
        deployment.addAllDependencies();
        container.start();
        container.deploy(deployment);
        
        Topology lookup = Topology.lookup();
        lookup.addListener(new TopologyListener() {
            @Override
            public void onChange(Topology tplg) {
                System.out.println("Delivery Service: The Topology Has Changed!");
            }
        });
        Map<String, List<Topology.Entry>> asMap = lookup.asMap();
        for(String key : asMap.keySet()){
            System.out.println("Key: "+ key + " - Value: "+asMap.get(key));
        }
        
    }
}
