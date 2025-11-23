# Case Service

A case worker service for simple case flow. 

An example that uses jpa to store data in a Posgresql database, flyway 
to manage database changes, ActiveMQ to push events and uses keycloak for 
api security.

## Requirements

### Apache ActiveMQ

An apache ActiveMQ server must be running locally.

### Keycloak

Set up a keycloak server locally and setup a realm with users. The 
keycloak server must have configured a scope with name case.

## Running the application

The application uses test containers to store the data. Before you start the application, make sure 
of the following:

1) Docker is running  
2) Keycloak is running on port 8080
3) ActiveMQ is running on port 61616

Run TestCaseServiceApplication to start testing the application.

The application api can be reached on port 8181.