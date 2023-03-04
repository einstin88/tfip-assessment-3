# Tips on developing your Spring Boot application
## Contents:
1. [Scaffolding](#1-scaffolding)
    - [What to create?](#b-what-to-create)

2. [Test run your application](#2-test-run)

3. [Implement your functions](#3-implementations)



## 0. Purpose / Background

We create Spring Boot applications with the Web & DevTools dependencies to act as a **Web Server with MVC design pattern**. The scope of the server is to listen to HTTP requests, process the requests (often involve validations) and return appropriate responses to the client.

---

## 1. Scaffolding
### Goal
> To generate the general file structure of your application where you'll create the typical classes to fill up with functions later

### A. Initialize project
Add the dependencies you need based on the task requirements. Be specific with what to add and never add everything that you *think is related,* otherwise there will be conflict in spring-boot's autoconfiguration that stops you from starting the application. 

### B. What to create
1. Folders
    - controller
    - service
    - repo
    - model
    - config (if you need to access redis or mongoDB)
    - data (to store DB-query helper files)
    - utils (optional)
![folder_structure](/img/folder-structure.png)

2. Classes (start from bottom to top)
    - AppConfig.java 
        > only when using Redis or MongoDB
    - yourAppRepo.java
        > Autowire the template - RedisTemplate/JdbcTemplate/MongoTemplate
    - yourAppService.java
        > Autowire the repo + inject a logger [^1]
    - yourAppController.java or yourAppRestController.java
        > Autowire the service + inject a logger [^1]
     
3. application.properties
    - add all the properties to connect to the database
    - enable logging by setting it to *debug*
        > logging.level.web=debug

[^1]: Inject logger by using *static* LoggerFactory or *@Slf4j* if you have lombok dependency
---

## 2. Test Run
### Goal
> To check if the application can boot up without errors. 
It's easy to forget one or few properties, or using an old connection string, so it's a good idea to check if you have set them correctly at the start. You may also want to check the connection to the DB 
---

## 3. Implementations
### A. Controllers
> Controllers are like routers, it's purpose is to handle incoming requests (i.e map requests to a function), pass the request to a service for further processing, and generate an appropriate response depending on the outcome of the 'service call'



## tfip-assessment-3
```
To run in development mode:
`mvn spring-boot:run -Dspring-boot.run.profile="dev"`
```
