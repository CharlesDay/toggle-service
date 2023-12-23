package com.charlie.toggleservice.config;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCosmosRepositories(basePackages = "com.charlie.toggleservice.repositories")
public class GlobalConfig extends AbstractCosmosConfiguration {

    @Autowired
    AzureBlobProperties azureBlobProperties;

    @Autowired
    AzureCosmosProperties azureCosmosProperties;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(azureBlobProperties.getConnectionString()).buildClient();
    }

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        return new CosmosClientBuilder()
                .endpoint(azureCosmosProperties.getUri())
                .key(azureCosmosProperties.getKey());
    }

    @Override
    protected String getDatabaseName() {
        return azureCosmosProperties.getDatabase();
    }
}
