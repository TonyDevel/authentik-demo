package com.authentik.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthentikDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentikDemoApplication.class, args);
    }

}
