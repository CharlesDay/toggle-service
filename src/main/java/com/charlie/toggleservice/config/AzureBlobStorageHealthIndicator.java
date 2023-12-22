package com.charlie.toggleservice.config;

import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AzureBlobStorageHealthIndicator implements HealthIndicator {

    private final String connectionString = System.getenv("AZURE_BLOB_CONNECTION_STRING");

    @Override
    public Health health() {
        try {
            new BlobServiceClientBuilder().connectionString(connectionString).buildClient()
                    .getBlobContainerClient("toggles").exists();

            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }
}