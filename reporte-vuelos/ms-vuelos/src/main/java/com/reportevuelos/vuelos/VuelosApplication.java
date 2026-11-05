package com.reportevuelos.vuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VuelosApplication {

    public static void main(String[] args) {
        SpringApplication.run(VuelosApplication.class, args);
    }
}
