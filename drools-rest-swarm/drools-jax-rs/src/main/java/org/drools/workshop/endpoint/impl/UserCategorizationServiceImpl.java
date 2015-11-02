package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.drools.workshop.model.User;

import org.drools.workshop.endpoint.api.UserCategorizationService;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class UserCategorizationServiceImpl implements UserCategorizationService {

    @Inject
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-user-kjar", version = "1.0-SNAPSHOT")
    @KSession
    private KieSession kSession;

    public UserCategorizationServiceImpl() {
    }

    @Override
    public User categorizeUser(User user) {
        System.out.println(">> kSession: " + kSession);
        printKieSessionAllFacts(kSession);
        System.out.println(">> User: " + user);
        kSession.insert(user);
        int fired = kSession.fireAllRules();
        System.out.println(">> Fired: " + fired);
        return user;

    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        for (Object o : kSession.getObjects()) {
            if (o instanceof User) {
                users.add((User) o);
            }
        }
        return users;
    }

    private void printKieSessionAllFacts(KieSession kSession) {
        System.out.println(" >> Start - Printing All Facts in the Kie Session");
        for (Object o : kSession.getObjects()) {
            System.out.println(">> Fact: " + o);
        }
        System.out.println(" >> End - Printing All Facts in the Kie Session");
    }

}
