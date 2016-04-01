package org.drools.workshop.restaurant.endpoint.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.restaurant.endpoint.api.NodeStatsService;
import org.drools.workshop.restaurant.endpoint.exception.BusinessException;
import org.drools.workshop.restaurant.model.Stats;
import org.kie.api.builder.ReleaseId;
import org.drools.workshop.restaurant.endpoint.api.RestaurantLocationService;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class NodeStatsServiceImpl implements NodeStatsService {


    public NodeStatsServiceImpl() {
    }

    @Override
    public Stats getStats() throws BusinessException {
//        ReleaseId releaseId = ((FoodRecommendationServiceImpl) service).getkContainer().getReleaseId();

        return new Stats(System.getenv().get("HOSTNAME"),"version 1");
//                releaseId.getGroupId()
//                + ":" + releaseId.getArtifactId() + ":" + releaseId.getVersion() + "-" + releaseId.isSnapshot());
    }

}
