package com.sandbox.springsleuth.dal.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.sandbox.springsleuth.dal.entity")
@EnableJpaRepositories(value = "com.sandbox.springsleuth.dal.repository")
public class JpaConfig {

}
