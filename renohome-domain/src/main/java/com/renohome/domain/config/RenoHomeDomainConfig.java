package com.renohome.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = { "com.renohome.domain.entity"})
@EnableJpaRepositories("com.renohome.domain.repo")
public class RenoHomeDomainConfig {

}
