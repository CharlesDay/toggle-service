package com.charlie.toggleservice.config;

import com.charlie.toggleservice.repositories.AzureCosmosRepository;
import com.charlie.toggleservice.services.ToggleServiceCosmosDb;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "toggle.service", havingValue = "azureCosmos")
public class AzureCosmosConfig {


    @Bean
    public ToggleServiceCosmosDb toggleServiceCosmosDb(AzureCosmosRepository repository) {
        return new ToggleServiceCosmosDb(repository);
    }
}
