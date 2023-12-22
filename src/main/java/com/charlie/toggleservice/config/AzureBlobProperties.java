package com.charlie.toggleservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "azure.blob")
@Data
public class AzureBlobProperties {
    private String containerName;
    private String connectionString;
}
