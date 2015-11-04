####### BASE ############
FROM jboss/wildfly:8.1.0.Final

####### MAINTAINER ############
MAINTAINER "Salaboy" "salaboy@redhat.com"

####### ENVIRONMENT ############
ENV JAVA_OPTS -XX:MaxPermSize=256m -Xms256m -Xmx512m -Dkie.maven.settings.custom=/opt/jboss/.m2/settings.xml -Dorg.jbpm.server.ext.disabled=true
ENV JBOSS_BIND_ADDRESS 0.0.0.0
LABEL org.kie.kie-server.artifact=${kie.docker.kie-server.wildfly8.repositoryPath}

####### SYTEM USERS FOR DEVELOPMENT ############
USER root
# Set a password for root & jboss users (for any further system operations, etc)
RUN echo "root:rootman" | chpasswd && \
echo "jboss:jboss" | chpasswd

####### KIE-SERVER & WILDFLY8 CONFIG FILES ############
# Latest WAR from Maven repository & Custom Wildfly configuration files
# There is an issue in Docker with ADD command.
# When a file is added into the container's filesystem, the file owner is always root, instead of the current running user.
# See https://github.com/docker/docker/issues/5110
# The workaround is doing a chown using root user and then switchback to jboss user.

# Wildfly custom configuration.
ADD etc/kie-server-users.properties $JBOSS_HOME/standalone/configuration/kie-server-users.properties
ADD etc/kie-server-roles.properties $JBOSS_HOME/standalone/configuration/kie-server-roles.properties
ADD etc/standalone-full-kie-server.xml $JBOSS_HOME/standalone/configuration/standalone-full-kie-server.xml


# Custom kie-server Wildfly startup scripts.
ADD etc/start_kie-server.sh $JBOSS_HOME/bin/start_kie-server.sh

# Add the kie-server WAR artifact from the assembly result.
ADD maven/kie-server.war $JBOSS_HOME/standalone/deployments/kie-server-archive.war

# The project site report for the build
ADD maven/site $JBOSS_HOME/standalone/deployments/site.war

# Work with unpacked WAR archives in Wildfly, so other images can override webapp files (such as persistence.xml,etc)
RUN unzip -q -d $JBOSS_HOME/standalone/deployments/kie-server.war $JBOSS_HOME/standalone/deployments/kie-server-archive.war && \
touch $JBOSS_HOME/standalone/deployments/kie-server.war.dodeploy && \
rm -rf $JBOSS_HOME/standalone/deployments/kie-server-archive.war && \
unzip -q -d $JBOSS_HOME/standalone/deployments/site.war $JBOSS_HOME/standalone/deployments/site.war/kie-server-site.jar && \
rm -rf $JBOSS_HOME/standalone/deployments/site.war/kie-server-site.jar && \
touch $JBOSS_HOME/standalone/deployments/site.war.dodeploy

# Set right permissions for jboss user.
RUN chown -R jboss:jboss $JBOSS_HOME/standalone/deployments/* && \
chmod +x $JBOSS_HOME/bin/*.sh && \
chmod 777 -R /opt/jboss/ && \
chown jboss:jboss -R /opt/jboss/ && \
chown jboss:jboss $JBOSS_HOME/bin/start_kie-server.sh && \
chown jboss:jboss $JBOSS_HOME/standalone/configuration/kie-server-users.properties && \ 
chown jboss:jboss $JBOSS_HOME/standalone/configuration/kie-server-roles.properties && \
chown jboss:jboss $JBOSS_HOME/standalone/configuration/standalone-full-kie-server.xml && \
chown -R jboss:jboss $JBOSS_HOME/modules/system/layers/base/* 

# Switchback to jboss user
#USER jboss

# M2 Settings: 
ADD etc/settings.xml /opt/jboss/.m2/settings.xml


####### COMMAND ############
WORKDIR $JBOSS_HOME/bin/
CMD ["./start_kie-server.sh"]
