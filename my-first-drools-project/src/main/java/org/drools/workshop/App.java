package org.drools.workshop;

import javax.inject.Inject;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

/**
 * Hello Drools world!
 *
 */
public class App {

    @Inject
    @KSession
    private KieSession kSession;

    public void bootstrapDrools() {
        // The KieSession was injected so we can use it now
        kSession.insert("Hi There!");
        int rulesFired = kSession.fireAllRules();
        System.out.println(">>> Rules Fired: "+rulesFired);
        
        
    }

    public static void main(String[] args) {
        //Boostrap the CDI container, in this case WELD
        Weld w = new Weld();

        WeldContainer wc = w.initialize();
        App app = wc.instance().select(App.class).get();
        app.bootstrapDrools();

        w.shutdown();
    }
}
