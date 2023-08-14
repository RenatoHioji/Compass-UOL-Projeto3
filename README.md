# Compass-UOL-Projeto-3

## Description
The project is based on the third challenge where we should construct an asynchronous API, following a pipe line for a post processing.

## Prerequisites
- Java 17 or higher installed;
- An IDE to run the Java application;
- Some program to access the endpoints;
- ActiveMQ downloaded.

#

## Dependencies
- ActiveMQ - Used to integrate ActiveMQ to application;
- Flyway - Used to insert database data in the application's starts;
- h2: Embedded database;
- jackson-databind: responsible for facilitating the conversion between JAVA and JSON objects;
- jwtWebToken: Used for spring security JWT application;
- Lombok: Used to reduce repetitive code by generating standard methods through annotations;
- Spring Boot Security - Used to configure all the security's configuration, like tokens, authentications and authorizations;
- Spring Cloud (Feign Client) - Used to consume external API information;
- Spring Data Jpa - Used to connect with database with JpaRepository interface;
- Spring Doc Open AI - Swagger dependency;
- Spring Starter Web - Essential spring boot dependecy for constructing WEB REST API;


## Technologies used
- ActiveMQ;
- Flyway;
- H2 (Database);
- Java;
- JWT;
- Lombok;
- Spring Boot;
- Spring Web;
- Spring Security;
- Spring Cloud (Feign Client);
- Spring Data Jpa;
- Swagger.

# Directories
- Config -> Configuration for spring security, Jms and Flyway.
- Entity -> The "models" for usage.
- DTO -> Records used to manage data transfer inside the api.
- Controller -> Interfaces and Classes implementations for controlling http requests.
- Service -> Interfaces and Classes implementations for manage business logic.
- Repository -> Interface to connect the application to database.
- Exceptions -> Classes used to handle throwing exception.
- Fegin Client -> Interface used to connect with a external API to obtain some data.
- JWS -> Classe constructed to consume queues and message.

## Endpoints
### Post API

#### GET

- /posts -> Find all posts processed.

#### POST

- /posts/{postId} -> Process a new post.

#### PUT

- /posts/{postId} -> Reprocess a post.

#### DELETE

- /{postId} -> Disable a post.

### Security API

#### POST

- /api/security -> Login in the application.
- /api/security/register -> Register a new account.

# DTO

## POST Response

- PostDTOResponse {
    id long
    title string
    body string
    comments:
        id long
        body string
        postId long
    history:
        id long
        date instant
        status HistoryEnum(string)
        postId long
    }

## Security Request
- RegisterDTORequest{
    name string
    username string
    email string
    password string
    }

- LoginDTORequest{
    usernameOrEmail string
    password string
    }


# Database
It was chosen the H2 database, because it is embedded, so installation is needed, being practical and easy to anyone use it.
- Access to H2 database:
  URL: localhost:8080/h2-console/
  JDBC URL: jdbc:h2:mem:testdb
  Username: admin;
  Password: admin;
  Creating type: CREATE-DROP.

# Security
For security, it was used Spring Security with CORS layer, CSRF for protection and JWT for authentication and authorization.

# Swagger
The API's documentation is based on Swagger UI and uses its interactivity to test the endpoints and the structure of the project.
- Access to Swagger:
  URL: localhost:8080/swagger-ui/index.html

# How to run the application

## ActiveMQ
- It's needed to turn on the activeMQ, for that, its necessary to pick up ActiveMQ bin's URL, then access it with Windows Shell and run "activemq start".

## Application
- Access the project "challenge3" folder and run "ChallengeApplication";
- Manage all the security and post endpoints into Insomnia or another program to access the endpoint;
- First it's necessary to use the GET method of post's endpoint and pickup the CSRF token in header;
- Second, you need to add the CSRF token to execute the register endpoint, then login in application;
- Third, the login will return an JWT token that is required in all the post's methods, besides the GET;
- Forth, add the JWT token in "BEARER" auth section and CSRF token;
- Now, with this, it's possible to run all the application methods.

