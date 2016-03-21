package org.drools.workshop.food.endpoint.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.drools.core.command.runtime.rule.QueryCommand;
import org.drools.workshop.food.endpoint.exception.BusinessException;
import org.drools.workshop.food.endpoint.api.FoodRecommendationService;
import org.drools.workshop.food.model.Meal;
import org.drools.workshop.food.model.Person;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class FoodRecommendationServiceImpl implements FoodRecommendationService {

    @Inject
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-food-kjar", version = "1.0-SNAPSHOT")
    private KieContainer kContainer;

    public FoodRecommendationServiceImpl() {
    }

    @Override
    public Meal recommendMeal(Person person) throws BusinessException {
        StatelessKieSession kieSession = kContainer.newStatelessKieSession();

        InsertObjectCommand insertObjectCommand = new InsertObjectCommand(person);
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        QueryCommand queryCommand = new QueryCommand("results", "getMeal", (Object[]) null);
        List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
        commands.add(insertObjectCommand);
        commands.add(fireAllRulesCommand);
        commands.add(queryCommand);
        BatchExecutionCommand batch = new BatchExecutionCommandImpl(commands);

        ExecutionResults results = kieSession.execute(batch);
        QueryResults queryResults = (QueryResults) results.getValue("results");
        Iterator<QueryResultsRow> iterator = queryResults.iterator();
        while (iterator.hasNext()) {
            Meal meal = (Meal) iterator.next().get("$m");
            if (meal != null) {
                System.out.println("Meal : " + meal);
                return meal;
            }
        }

        return null;
    }

    public KieContainer getkContainer() {
        return kContainer;
    }
    
    

}
