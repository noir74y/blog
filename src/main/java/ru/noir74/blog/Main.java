package ru.noir74.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.noir74.blog.util.config.ApplicationConfig;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //SpringApplication.run(Main.class, args);
        ApplicationContext context1 = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }
}