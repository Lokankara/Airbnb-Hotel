package com.manager.hotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.manager.hotel.dao"})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {
}
