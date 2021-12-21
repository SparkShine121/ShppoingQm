package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:kaptcha.xml"})
public class ShppoingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShppoingApplication.class, args);
    }


}
