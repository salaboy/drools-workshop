#!/usr/bin/env bash

# Start Wildfly with some parameters.
./standalone.sh  -b $JBOSS_BIND_ADDRESS --server-config=standalone-full-kie-server.xml 
exit $?
