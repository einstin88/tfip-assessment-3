# Tips on developing your Spring Boot application
## Contents:
1. [Scaffolding](#1-scaffolding)
    - [What to create?](#b-what-to-create)

2. [Test run your application](#2-test-run)

3. [Implement your functions](#3-implementations)
    - [Controllers](#a-controllers)



## 0. Purpose / Background

We create Spring Boot applications with the Web & DevTools dependencies to act as a ==Web Server with MVC design pattern==. The scope of the server is to listen to HTTP requests, process the requests (often involve validations) and return appropriate responses to the client.

Java is an OOP language. Therefore, in Spring, each class (i.e. your bean) *should be* scoped to perform similar tasks. For example, @controller for routing, @service for business logic, @repository for querying data from DB, Model classes for storing form/DB data, etc.. 

It's the same reason as categorizing products in the supermarket lanes. With this approach, it will make your application much easier to debug during development because it'll be more instinctive to find the codes/functions that is generating the error.

---

## 1. Scaffolding
### Goal
> To generate the general file structure of your application where you'll create the typical classes to fill up with functions later

### A. Initialize project
Add the dependencies you need based on the task requirements. Be specific with what to add and never add everything that you *think is related,* otherwise there will be conflict in spring-boot's autoconfiguration that stops you from starting the application. 

### B. What to create
1. **Folders**
    - controller
    - service
    - repo
    - model
    - config (if you need to access redis or mongoDB)
    - data (to store DB-query helper files)
    - utils (optional)

![folder_structure](/img/folder-structure.png)


2. **Classes** (start from bottom to top)
    - AppConfig.java 
        > only when using Redis or MongoDB
    - yourAppRepo.java
        > Autowire the template - RedisTemplate/JdbcTemplate/MongoTemplate
    - yourAppService.java
        > Autowire the repo + inject a logger [^1]
    - yourAppController.java or yourAppRestController.java
        > Autowire the service + inject a logger [^1]
     
3. **application.properties**
    - add all the properties to connect to the database
    - enable logging by setting it to *debug*
        > logging.level.web=debug

[^1]: Inject logger by using *static* LoggerFactory or *@Slf4j* if you have lombok dependency
---

## [2. Test Run](#contents)
### Goal
> To check if the application can boot up without errors before you add any of your fancy functions into it.

It's easy to forget one or few properties, or using an old connection string, so it's a good idea to check if you have set them correctly at the start. You may also want to check if the connection to the DB hosted on the cloud is working.

So, don't wait until you implement everything to start your server. DevTools allows you to make changes to your code and helps you restart the server automatically anyway.[^2]

[^2]: If you make changes to the POM or properties file, you'll have to restart the server for them to take effect.

---

## [3. Implementations](#contents)
### A. Controllers
> Controllers are like routers, it's purpose is to handle incoming requests (i.e map requests to a function), pass the request to a service for further processing, and generate an appropriate response depending on the outcome of the 'service call'

### B. Repo

### C. Model

### D. Service

### E. Utils


## tfip-assessment-3
```
To run in development mode:
mvn spring-boot:run -Dspring-boot.run.profile="dev"
```
