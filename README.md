## tfip-assessment-3
To run in development mode:
`mvn spring-boot:run -Dspring-boot.run.profile="dev"`


# Tips on developing your Spring Boot application
1. [Scaffolding]()
2. [Test run your application]()
3. [Implement your functions]()

---

## 1. Scaffolding {#scaffold}
### **Goal**
: to generate the general file structure of your application where you'll create the typical classes to fill up with functions later

### a. Initialize project
Add the dependencies you need based on the task requirements. Be specific with what to add and never add everything that you *think is related,* otherwise there will be conflict in spring-boot's autoconfiguration that stops you from starting the application. 

### c. What to create
1. Folders
    - controller
    - service
    - repo
    - model
    - config (if you need to access redis or mongoDB)
    - utils (optional)
2. Classes
    - <yourApp>Controller or <yourApp>RestController
3. application.properties