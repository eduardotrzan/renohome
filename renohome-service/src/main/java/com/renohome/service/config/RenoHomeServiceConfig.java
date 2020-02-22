package com.renohome.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.domain.config.RenoHomeDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.service")
@Import({ RenoHomeDomainConfig.class })
public class RenoHomeServiceConfig {

}
