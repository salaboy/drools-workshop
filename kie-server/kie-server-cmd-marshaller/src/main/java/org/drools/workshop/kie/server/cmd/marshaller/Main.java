/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.workshop.kie.server.cmd.marshaller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.GetObjectsCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.drools.workshop.model.User;
import org.kie.server.api.marshalling.Marshaller;
import org.kie.server.api.marshalling.MarshallerFactory;
import org.kie.server.api.marshalling.MarshallingFormat;

/**
 *
 * @author salaboy
 */
public class Main {

    public static void main(String[] args) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(User.class);
        Marshaller marshaller = MarshallerFactory.getMarshaller(classes,  MarshallingFormat.JSON, Main.class.getClassLoader());
//        String marshalled = marshaller.marshall(new CreateContainerCommand(new KieContainerResource("sample", new ReleaseId("org.drools.workshop",  "drools-user-kjar", "1.0-SNAPSHOT"))));
        User user = new User();
        user.setAge(32);
        user.setName("salaboy");
//        String marshalled = marshaller.marshall(new InsertObjectCommand(user, "my-user-fact"));
        InsertObjectCommand insertCmd = new InsertObjectCommand(user, "my-user-fact");
        
        List<GenericCommand<?>> cmds = new ArrayList<GenericCommand<?>>();
        cmds.add(insertCmd);
        cmds.add(new FireAllRulesCommand("fired"));
        cmds.add(new GetObjectsCommand(null,"facts"));
        String marshalled = marshaller.marshall(new BatchExecutionCommandImpl(cmds));
        System.out.println(">>> " + marshalled);
    }
}
