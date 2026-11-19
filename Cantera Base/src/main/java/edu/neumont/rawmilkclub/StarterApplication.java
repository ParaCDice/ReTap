package edu.neumont.rawmilkclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the whole app: Spring finds every @RestController (and other @Component class)
 * in this package and below, wires them together, and starts the built-in Tomcat on 8080.
 * Unlike Module 1 there is no separate Tomcat and no WAR file: this main method is the server.
 */
@SpringBootApplication
public class StarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

}
