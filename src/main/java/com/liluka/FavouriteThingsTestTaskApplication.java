package com.liluka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FavouriteThingsTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(FavouriteThingsTestTaskApplication.class, args);
    }
}
