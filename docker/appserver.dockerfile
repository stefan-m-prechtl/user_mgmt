# Basis-Image
FROM payara/server-full:5.191

# Enable debug mode
ENV PAYARA_ARGS --debug

# Guava
COPY guava-23.0.jar /opt/payara/appserver/glassfish/domains/production/lib
# MYSQL-JDBC-Driver 
COPY mysql-connector-java-8.0.12.jar /opt/payara/appserver/glassfish/domains/production/lib
 
# ASADMIN Commands to create JDBC-Resource
COPY --chown=payara:payara ./post-boot-commands.asadmin /opt/payara/config/
