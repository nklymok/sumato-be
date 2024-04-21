package com.naz_desu.sumato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("com.naz_desu")
@SpringBootApplication
public class SumatoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SumatoApplication.class, args);
    }

}
