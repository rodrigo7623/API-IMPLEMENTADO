package com.api.desa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.api.desa"})
public class Aplicacion {
    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args);
    }
}
