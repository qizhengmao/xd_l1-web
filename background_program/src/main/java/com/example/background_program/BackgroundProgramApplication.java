package com.example.background_program;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.Mapping;

@SpringBootApplication(excludeName = "com.example.background_program.controller")
public class BackgroundProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgroundProgramApplication.class, args);
    }

}
