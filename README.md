# Customer Statement Processor - Rabobank #

### Assignment ###

Implement a REST service which receives the customer statement JSON as a POST data, Perform the below validations
  1. All transaction references should be unique
  2. The end balance needs to be validated ( Start Balance +/- Mutation = End Balance )

### Application StartUp
   #### IDE
 * Import the project in your favourite IDE
 * Run the file `src/main/java/com/rabobank/statementprocessor/Application.java`
 
  #### Command Line
 * Clone this repository
 * Run `mvn clean package`
 * Run `mvn spring-boot:run`
 
## Swagger URL
 
    http://localhost:8088/swagger-ui.html

## Request URL & Sample Request for POSTMAN

    POST Method :: http://localhost:8088/customer/statement/processor
    [
      {
        "Reference": 194261,
        "AccountNumber": "NL91RABO0315273637",
        "Description": "Clothes from Jan Bakker",
        "Start Balance": 21.6,
        "Mutation": -41.83,
        "End Balance": -20.23
      },
      {
        "Reference": 195446,
        "AccountNumber": "NL74ABNA0248990274",
        "Description": "Flowers for Willem Dekker",
        "Start Balance": 26.32,
        "Mutation": "+48.98",
        "End Balance": 75.3
      }
    ]
    
    

## Tools & Technology
  * JAVA 8
  * Microservies - SpringBoot
  * JUnit 4 
  * Intellij IDEA
  * Apache Maven
  * GitHub
  
## Coding 
 * API developed using S.O.L.I.D Coding Principles
 * JUnit and Integration Test cases has be written with overall code coverage of 95%
 * Lombok plugin has been used to avoid boilerplate code
 * Null check is being done in record level for { "Reference", "AccountNumber", "Start Balance", "Mutation", "End Balance" }
   as they are required fields to run the business validation.
 * AtomicBoolean.class from Java Concurrency package which is thread safe is used to access not final variable inside Lambda expression.   
 * Custom Validator has been implemented to Validate the Request object.
 