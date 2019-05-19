package org.might.projman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"org.might.projman"})
public class ProjmanApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProjmanApplication.class);
        app.run(args);
    }
}
