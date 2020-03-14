package com.renohome.home.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.renohome.home.domain.config.RenoHomeHomeDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.renohome.home.service")
@Import({ RenoHomeHomeDomainConfig.class, ExternalApiConfig.class })
public class RenoHomeHomeServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
