package com.patricia.notification.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages =
        "com.patricia.notification.infrastructure.adapters.persistence.repository")
public class MongoConfig {
}