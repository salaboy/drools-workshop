package org.drools.workshop.food.endpoint.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.food.endpoint.api.FoodRecommendationService;
import org.drools.workshop.food.endpoint.api.NodeStatsService;
import org.drools.workshop.food.endpoint.exception.BusinessException;
import org.drools.workshop.food.model.Stats;
import org.kie.api.builder.ReleaseId;

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
