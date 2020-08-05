package com.rabobank.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the Customer Statement Processor Application
 * Starts as Spring Boot Application
 *
 * @author Sethuraman Sathiyamoorthy
 * @version 1.0
 * @since 1.0
 */

@SpringBootApplication
public class StatementProcessorApplication {

    /**
     * Main method to start the application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StatementProcessorApplication.class, args);

    }

}
