package org.drools.workshop.delivery.endpoint.impl;

import javax.enterprise.context.ApplicationScoped;
import org.drools.workshop.delivery.endpoint.api.NodeStatsService;
import org.drools.workshop.delivery.endpoint.exception.BusinessException;
import org.drools.workshop.delivery.model.Stats;

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
