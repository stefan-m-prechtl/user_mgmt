# Basis-Image
FROM payara/server-full:5.183

# Hostname für Datenbankserver
ENV DBSERVER user_dbserver

# MYSQL-JDBC-Driver 
COPY mysql-connector-java-8.0.12.jar /opt/payara5/glassfish/domains/domain1/lib
 
# ASADMIN Commands to create JDBC-Resource
COPY post-boot-commands.asadmin /opt/payara5/
