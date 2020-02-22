package com.renohome.domain.testutils;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.renohome.domain.repo" })
@EntityScan(basePackages = { "com.renohome.domain.entity" })
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = RenoHomeDatabasePrefillUtils.class)
class RenoHomeDomainRepoITConfig {

}
