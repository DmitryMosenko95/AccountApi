package ru.aston.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(AccountApiApplication::main).with(TestAccountApiApplication.class).run(args);
    }

}
