# Customer Statement Processor - Rabobank #

### Assignment ###

Implement a REST service which receives the customer statement JSON as a POST data, Perform the below validations
  1. All transaction references should be unique
  2. The end balance needs to be validated ( Start Balance +/- Mutation = End Balance )

### Application StartUp
   #####IDE
 * Import the project in your favourite IDE
 * Run the file `src/main/java/com/rabobank/statementprocessor/Application.java`
 
  #####Command Line
 * Clone this repository
 * Run `mvn clean package`
 * Run `mvn spring-boot:run`
 
## Swagger URL
 
    http://localhost:8088/swagger-ui.html

## Request URL

    http://localhost:8088/customer/statement/processor

##Coding 
 * API developed using S.O.L.I.D Coding Principles
 * JUnit and Integration Test cases has be written with overall code coverage of 95%
 * Lombok plugin is used to avoid boilerplate coding
 * Null check is being done in record level for { "Reference", "AccountNumber", "Start Balance", "Mutation", "End Balance" }
   as they are required fields to run the business validation.
 * Custom Validator has been written to Validate the Request object.
 