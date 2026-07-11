package com.ignaciorudyk.resource.auth.autoconfigure;

import com.ignaciorudyk.resource.auth.config.StarterResourceAuthProperties;
import com.ignaciorudyk.resource.auth.config.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "ignaciorudyk.resource.authorization",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ComponentScan(
        value = "com.ignaciorudyk.resource.auth",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityAutoConfiguration.class
        )
)
@EnableConfigurationProperties(StarterResourceAuthProperties.class)
public class AuthAutoConfiguration {}