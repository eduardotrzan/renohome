package com.renohome.home.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.home.service.config.RenoHomeHomeServiceConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.home.controller")
@Import({ RenoHomeHomeServiceConfig.class })
public class RenoHomeHomeControllerConfig {


}
