package com.patricia.notification.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB configuration for the notification service.
 *
 * <p>Enables auditing (for automatic {@code createdAt}/{@code updatedAt} population)
 * and scopes Spring Data MongoDB repository scanning to the infrastructure persistence
 * package to avoid conflicts with other repository types.</p>
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages =
        "com.patricia.notification.infrastructure.adapters.persistence.repository")
public class MongoConfig {
}
