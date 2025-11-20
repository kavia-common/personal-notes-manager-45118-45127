package com.example.notesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PUBLIC_INTERFACE
 * NotesbackendApplication is the Spring Boot entry point for the Notes Backend.
 *
 * Package: com.example.notesbackend
 * Visibility: public
 * Annotation: @SpringBootApplication
 *
 * This application serves REST endpoints and is configured to run on port 3001
 * via src/main/resources/application.properties.
 */
@SpringBootApplication
public class NotesbackendApplication {

    /**
     * PUBLIC_INTERFACE
     * Boot the Spring application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(NotesbackendApplication.class, args);
    }
}
