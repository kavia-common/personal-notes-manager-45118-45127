package com.example.notesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point for the Notes Backend.
 * Starts a Spring Boot app exposing REST endpoints on port configured in application.properties (3001).
 */
// PUBLIC_INTERFACE
@SpringBootApplication
public class NotesbackendApplication {
	/** Boot application. */
	public static void main(String[] args) {
		SpringApplication.run(NotesbackendApplication.class, args);
	}
}
