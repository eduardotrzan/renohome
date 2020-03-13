package com.renohome.contractor.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.contractor.domain.config.RenoHomeContractorDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.contractor.service")
@Import({ RenoHomeContractorDomainConfig.class })
public class RenoHomeContractorServiceConfig {

}
