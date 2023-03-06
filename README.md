# Tips on developing your Spring Boot application more quickly

## Contents:

1. [Scaffolding](#1-scaffolding)

   - [Goal](#goal)
   - [What to create?](#b-what-to-create)

2. [Test run](#2-test-run)

   - [Goal](#goal-1)

3. [Implementations](#3-implementations)

   - [Controllers](#a-controllers)
   - [Repos](#b-repo)
   - [Models](#c-model)
   - [Service](#d-service)
   - [Utils](#e-utils)

4. [.next()](#4-next)

## Credits:
1. Andrea for correcting my occasional mind-twisting English and for the many helpful inputs.
2. Chuk, Kenneth and Darryl for putting up a comprehensive syllabus and teaching us the details and nuances of developing web applications.

## 0. Purpose / Background

We create Spring Boot applications with the Web & DevTools dependencies to act as a **Web Server with MVC design pattern**. The scope of the server is to listen to HTTP requests, process the requests (often involving validations) and return appropriate responses to the client.

Java is an OOP language. Therefore, in Spring, each class (i.e. your bean) _should contain only_ functions of similar nature. This will be explained in more details in the sub-sections of [Part 3](#3-implementations). For example, @controller for routing, @service for business logic, @repository for querying data from DB, Model classes for holding form/DB data, etc..

The same approach is taken when categorizing products in the supermarket lanes. Such organization will make your application much easier to debug during development because it'll be more instinctive to find the codes/functions that are generating the error.

---

## 1. Scaffolding

### Goal

> To generate the general file structure of your application where you'll create the typical classes to fill up with functions later

### A) Initialize project

Add the dependencies you need based on the task requirements. Be specific with what to add and never add everything that you _think is related,_ otherwise there will be conflict(s) in spring-boot's auto-configuration that stops you from starting the application.

### B) What to create

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
     > Autowire the template - RedisTemplate/ JdbcTemplate/ MongoTemplate
   - yourAppService.java
     > Autowire the repo + inject a logger [^1]
   - yourAppController.java or yourAppRestController.java
     > Autowire the service + inject a logger [^1]

![file_structure](/img/folder-with-new-files.png)


3. **application.properties**
   - add all the properties to connect to the database
   - enable logging by setting it to *debug*
     > logging.level.web=debug

[^1]: Inject logger by using _static_ LoggerFactory or _[@Slf4j](https://projectlombok.org/features/log)_ if you have lombok dependency

---

## [2. Test Run](#contents)

### Goal

> To check if the application can boot up without errors before you add any of your fancy functions into it.

It's easy to forget to add necessary properties, or to update an old connection string when we reuse them, it's a good idea to check if you have set them correctly at the start. You may also want to check if the connection to the DB hosted on the cloud is working.

So, don't wait until you have implemented everything to start your server. DevTools automatically helps you to restart the server every time you make changes to your code.[^2]

[^2]: If you make changes to the POM or properties file, you'll have to restart the server for them to take effect.

---

## [3. Implementations](#contents)

### A) Controllers

> A controller is like router, it's purpose is to handle incoming requests (i.e map requests to a function), pass the request to a service for further processing, and generate an appropriate response depending on the outcome of the 'service call'

**Controller types:** @Controller is used to label a Java class that returns text/html while @RestController is to label a class that returns Json-formatted string. Additionally, annotate the class with @RequestMapping and specify its return type (eg. produces=MediaType.HTML_VALUE for @Controller class) so that there is no ambiguity to Spring Boot and other developers if you are working in a team.

> In short, put functions that return html to @Controller and json to @RestController

**Initial steps:** After your Spring Boot server is running, first create all the routes to your server, ie. create functions and annotate them with @getmapping/ @postmapping plus the path (eg. path="/api/transfer"). As you create these functions, you will also add the _@PathVariable_ and/or _@RequestParam_. Initially, use `return null;` so that there is no error. Add in `logger.info("your message")` and you can immediately start checking if your route + requestParam/pathVariable is working. Logging helps in printing out detailed messages in the terminal so that you can track of whether the codes you just added in work.

> Create all the routes so that you won't miss out on any of the requirements and can immediately start testing as you develop your server

**Next steps:** Once you have the routes, it's time to start thinking of the content to respond to the requests, ie. return what?

@Controller typically returns a html page, so start scaffolding a html file to return the desired view. Oftenly, you will also need some data to dynamically generate the page. It is then time to start implementing stuff in [repo](#b-repo) and [model](#c-model).

In @RestController, some data from DB or external API calls will be returned to the client. So, you will also be starting to do something in repo and model to be able to fetch data back for the controller.

### B) Repo

> Repository class' purpose is to perform CRUD on the database. Functions in the class should return mapped results[^3] directly. Leave the error handlings (eg. try-catch or null checks) and further data transformation/processing to the [service class](#d-service).

[^3]: Mapped results are data from the DB that has been mapped as a Java object or a collection of Java objects.

**Initial steps:** Think of what data is required to be passed back to the calling function? Most of the time, it's multiple attributes/columns from the database. To ensure data integrity across your server app, it's best to create a POJO with all the required attributes/columns in the [model](#c-model) folder. The POJO will be used by the template libraries to convert your data in Java's format to its native format before reading/writing to the database.

**Next steps:** When you have created the POJO, it's time to start using it as the return type in your query functions. Note that at times you want a list of data instead of a single object, so you'll have to think of which function in the template to use. The easy option is to look at Chuk's slides for examples. The better option in my opinion is to refer to the template library's documentation. I'll link them here for convenience.

- [RedisTemplate](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html)
- [JdbcTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
- [MongoTemplate](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html)

**Additional for MySQL**: query functions for MySQL require the writing of SQL strings. It's more organized to write these query strings in a separate file as constants (with `static final`)- normally called `Queries.java`- especially in enterprise applications as it's easier to manage. SQL has its own syntax and format to follow so it's good to refer to the documentation when you are still at the learning phase. (Thanks Daryl for showing us such documents exist). [Link to the SELECT statement](https://dev.mysql.com/doc/refman/8.0/en/select.html)

### C) Model

> Classes in _model_ are POJOs that has private attributes, getters, setters, toString and others. They are the objects that carry data throughout the server. Their purpose is 1) to map data from forms, or 2) to map a schema of a database's table/collection, or 3) to map data for a response by the controller (typically @restcontroller).

_Note:_ You may write static functions here to say, convert Json string to a class instance or vice versa. But according to Chuk, it's not very organized because it 'pollutes' the POJO and may create inefficiencies in large enterprise applications. The 'better' way is to write functions that transform data from one or more classes to another class in the `Utils.java` file.

**Steps:** They can be created with the help of `Source action...` on your IDE, using Lombok annotations (thanks Daryl again) or with Java Records (a bit more advanced to use). If it is a class to represent a form, you will most likely add the constraint annotations from Jakarta library here. [Link to the documentation of the constraint annotations.](https://javadoc.io/doc/jakarta.validation/jakarta.validation-api/latest/jakarta/validation/constraints/package-summary.html)

### D) Service

> The service class contains functions that perform business logics, ie. it will be called by the controller to process data or fetch data from DB or to transform data to another format, etc. Basically it is like a worker for the controller, so write your codes that do complicated logics here. API calls are made from the @service when needed. As such, it is the intermediary between controller and raw data.

**Initial steps:** Now that you have a repository function that might or might not return some data, you will write codes that do the check here. Depending on the template used in repo, the service may be checking for exceptions or if the return value is null.

**Custom validation of forms:** Sometimes there is a need to further validate data from a form, for example, tally the data from the form with data from somewhere else in the server. You have to write codes to do this and service is a good place to write them since service has access to the [repo](#b-repo). **Remember** to pass the `BindingResult` to your function, so that you can add any failed validation as an error. When the controller checks the `BindingResult` after calling your validation function, it can detect the errors and send the appropriate response back to the requestor.

**Next steps:** Upon doing what you need to in the service, your function is now ready to be called from the controller and you can start doing tests to make sure it works. I'm not knowledgable enough to do unit tests yet, my go-to is using the logger to display what's going on in my service.

### E) Utils

> The Utils folder/class is created to store helper functions that should be static. It is like the role of a clerk who does many miscelleneous tasks but is nonetheless important in keeping our code base neat and tidy.

**When to use:** Typically when you have some logics that make your function look bloated, you can put the part where it doesn't need to touch the repo or other Spring beans over here in utils. When there are codes that are repeated a few times and don't touch other beans, put them here. Static functions in your POJO? put them here too.

---

## [4. .next()](#contents)

Repeat the implementations again on another resource path, starting with the controller - is your controller's function receiving that request and data? Scaffold a simple html page to check before adding every piece of required data into it. Consider what do you need to do with the request and data. Is there data that you need to fetch from the database? If you have fetched the data, do you need to process them before returning to the controller?

## == END ==

---

## tfip-assessment-3

```
To run in development mode:
mvn spring-boot:run -Dspring-boot.run.profile="dev"
```
