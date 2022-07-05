package com.partyup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

@SpringBootApplication
public class PartyUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartyUpApplication.class, args);
    }
}
