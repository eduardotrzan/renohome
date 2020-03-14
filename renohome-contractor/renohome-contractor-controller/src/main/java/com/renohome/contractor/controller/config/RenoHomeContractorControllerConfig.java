package com.renohome.contractor.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.contractor.service.config.RenoHomeContractorServiceConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.contractor.controller")
@Import({ RenoHomeContractorServiceConfig.class })
public class RenoHomeContractorControllerConfig {


}
