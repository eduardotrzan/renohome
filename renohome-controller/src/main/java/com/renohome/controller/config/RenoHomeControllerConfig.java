package com.renohome.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.service.config.RenoHomeServiceConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.controller")
@Import({ RenoHomeServiceConfig.class })
public class RenoHomeControllerConfig {


}
