package org.drools.workshop.delivery.endpoint.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.delivery.endpoint.exception.BusinessException;
import org.drools.workshop.delivery.model.Delivery;
import org.drools.workshop.delivery.endpoint.api.DeliveryService;
import org.drools.workshop.restaurant.model.Restaurant;
import org.kie.api.cdi.KContainer;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class DeliveryServiceImpl implements DeliveryService {

    @Inject
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-delivery-kjar", version = "1.0-SNAPSHOT")
    private KieContainer kContainer;

    private final Map<String, KieSession> onGoingDeliveries = new HashMap<String, KieSession>();

    public DeliveryServiceImpl() {

    }

    @Override
    public Delivery getMockDelivery() throws BusinessException {
        Restaurant restaurant = new Restaurant("Carluccios", 10.0, 10.0);
        return new Delivery(restaurant, 15.0, 15.0);
    }
    
    
    

    @Override
    public Delivery getDelivery(String deliveryId) throws BusinessException {
        KieSession kieSession = onGoingDeliveries.get(deliveryId);
        QueryResults queryResults = kieSession.getQueryResults("getDelivery", new Object[]{});
        for (QueryResultsRow qrr : queryResults) {
            return (Delivery) qrr.get("$d");
        }
        return null;
    }

    @Override
    public String newDelivery(Delivery delivery) throws BusinessException {
        KieSession newKieSession = kContainer.newKieSession();
        newKieSession.insert(delivery);
        newKieSession.fireAllRules();
        String id = UUID.randomUUID().toString();
        onGoingDeliveries.put(id, newKieSession);
        return id;
    }

    @Override
    public List<String> getAllDeliveriesIds() throws BusinessException {
        return new ArrayList<String>(onGoingDeliveries.keySet());
    }

}
