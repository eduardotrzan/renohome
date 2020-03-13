package com.renohome.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.renohome.controller.config.RenoHomeControllerConfig;
import com.renohome.generic.security.config.SpringSecurityConfig;
import com.renohome.generic.tracking.config.TracingConfig;
import com.renohome.server.config.ApplicationConfig;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({
                                       ApplicationConfig.class
                               })
@Import({
                // Controller Modules
                RenoHomeControllerConfig.class,
                SpringSecurityConfig.class,
                TracingConfig.class,

        })
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ApplicationConfig config;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(this.config.getTimeZone()));
    }

    @Override
    public void run(String... strings) {
        log.info("Running system with config={}.", this.config);
    }
}
