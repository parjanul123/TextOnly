package com.textonly.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TextOnlyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextOnlyBackendApplication.class, args);
        System.out.println("✅ Serverul TextOnly rulează pe http://localhost:8080");
    }
}
