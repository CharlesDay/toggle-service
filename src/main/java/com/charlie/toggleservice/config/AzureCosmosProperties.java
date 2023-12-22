package com.charlie.toggleservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "azure.cosmos")
@Data
public class AzureCosmosProperties {
    private String key;
    private String uri;
    private String database;
}