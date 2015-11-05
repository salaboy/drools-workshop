package org.drools.workshop.endpoint.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.drools.workshop.cepmodel.BlackListedClient;
import org.drools.workshop.cepmodel.Client;
import org.drools.workshop.cepmodel.FraudSuspicion;
import org.drools.workshop.cepmodel.Transaction;
import org.drools.workshop.endpoint.api.TransactionEventService;
import org.drools.workshop.endpoint.exception.BusinessException;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class TransactionEventServiceImpl implements TransactionEventService, Channel {

    @Inject
    @KSession("cepRT")
    @KReleaseId(groupId = "org.drools.workshop", artifactId = "drools-cep-kjar", version = "1.0-SNAPSHOT")
    private KieSession kSession;
    
    private Set<FraudSuspicion> detections = new HashSet<FraudSuspicion>();

    @PostConstruct
    public void after() {
    	kSession.registerChannel("auditing", this);
    	kSession.addEventListener(new DebugAgendaEventListener(System.out));
    	kSession.addEventListener(new DebugRuleRuntimeEventListener(System.out));
    }
    
    public TransactionEventServiceImpl() {
    }

    @Override
    public void addTransaction(Transaction event) throws BusinessException {
    	kSession.insert(event);
    	kSession.fireAllRules();
    }
    
    @Override
    public void blacklist(String name) throws BusinessException {
    	kSession.insert(new BlackListedClient(new Client(name)));
    	kSession.fireAllRules();
    }
    
    @Override
    public List<FraudSuspicion> getFraudSuspicions() throws BusinessException {
    	return new ArrayList<FraudSuspicion>(detections);
    }

	@Override
	public void send(Object object) {
		detections.add((FraudSuspicion) object);
	}
}
