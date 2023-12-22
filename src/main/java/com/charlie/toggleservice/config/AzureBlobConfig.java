package com.charlie.toggleservice.config;

import com.charlie.toggleservice.repositories.AzureBlobRepository;
import com.charlie.toggleservice.services.ToggleService;
import com.charlie.toggleservice.services.ToggleServiceAzureBlob;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "toggle.service", havingValue = "azureBlob")
public class AzureBlobConfig {

    @Bean
    public ToggleService toggleService(AzureBlobRepository repository) {
        return new ToggleServiceAzureBlob(repository);
    }
}
