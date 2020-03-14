package com.renohome.home.domain.testutils;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.renohome.home.domain.repo" })
@EntityScan(basePackages = { "com.renohome.home.domain.entity" })
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = RenoHomeDatabasePrefillUtils.class)
class RenoHomeDomainRepoITConfig {

}
