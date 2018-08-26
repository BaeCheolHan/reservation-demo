package com.demo.reservation.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Booter {

    public static void main(String[] args) {

        SpringApplication.run(Booter.class, args);
    }
}
