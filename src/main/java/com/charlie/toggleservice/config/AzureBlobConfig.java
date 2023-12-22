package com.charlie.toggleservice.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.charlie.toggleservice.repositories.AzureBlobRepository;
import com.charlie.toggleservice.services.ToggleService;
import com.charlie.toggleservice.services.ToggleServiceAzureBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "toggle.service", havingValue = "azureBlob")
public class AzureBlobConfig {

    @Autowired
    private AzureBlobProperties azureBlobProperties;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(azureBlobProperties.getConnectionString()).buildClient();
    }

    @Bean
    public ToggleService toggleService(AzureBlobRepository repository) {
        return new ToggleServiceAzureBlob(repository);
    }
}
