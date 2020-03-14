package com.renohome.home.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.renohome.home.domain.config.RenoHomeHomeDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.home.service")
@Import({ RenoHomeHomeDomainConfig.class })
public class RenoHomeHomeServiceConfig {

}
