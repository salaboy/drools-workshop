/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.drools.food.controller;

import io.fabric8.kubernetes.api.model.DoneableReplicationController;
import io.fabric8.kubernetes.api.model.DoneableService;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerStatus;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceStatus;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.dsl.ClientResource;
import io.fabric8.kubernetes.client.dsl.ClientRollableScallableResource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salaboy
 */
public class Main {

    public static void main(String[] args) {

        String replicationControllerName = "drools-controller";
        String serviceName = "droolsservice";
        String namespace = "default"; // Openshift == project
        String image = "salaboy/drools-food-services";
        DefaultKubernetesClient kube = new DefaultKubernetesClient();

        ClientRollableScallableResource<ReplicationController, DoneableReplicationController> resource = kube.replicationControllers().inNamespace("default").withName(replicationControllerName);
        if (resource != null) {
            ReplicationController rc = resource.get();
            if (rc != null) {
                ReplicationControllerStatus status = rc.getStatus();
                Integer replicas = status.getReplicas();
            } else {
                kube.replicationControllers().inNamespace(namespace).createNew()
                        .withNewMetadata().withName(replicationControllerName).addToLabels("app", "drools").endMetadata()
                        .withNewSpec().withReplicas(1)
                        .withNewTemplate()
                        .withNewMetadata().withName(replicationControllerName).addToLabels("app", "drools").endMetadata()
                        .withNewSpec()
                        .addNewContainer().withName("drools").withImage(image)
                        // .addNewPort().withContainerPort(8080).withHostPort(8080).endPort()
                        .endContainer()
                        .endSpec()
                        .endTemplate()
                        .endSpec().done();
            }
        }
        ClientResource<Service, DoneableService> serviceResource = kube.services().inNamespace(namespace).withName(serviceName);
        if (serviceResource != null) {
            Service service = serviceResource.get();
            if (service != null) {
                ServiceStatus status = service.getStatus();
            } else {
                kube.services().inNamespace(namespace).createNew()
                        .withNewMetadata().withName(serviceName).endMetadata()
                        .withNewSpec()
                        .addToSelector("app", "drools")
                        .addNewPort().withPort(80).withNewTargetPort().withIntVal(8080).endTargetPort().endPort()
                        .endSpec()
                        .done();
            }
        }
    }
}
