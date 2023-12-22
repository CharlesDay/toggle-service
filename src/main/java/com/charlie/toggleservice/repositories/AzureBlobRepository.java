package com.charlie.toggleservice.repositories;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.charlie.toggleservice.model.FeatureToggle;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class AzureBlobRepository {

    private final BlobServiceClient blobServiceClient;
    private final ObjectMapper objectMapper;
    private static final String BLOB_CONTAINER_NAME = "toggles";

    @Autowired
    public AzureBlobRepository(BlobServiceClient blobServiceClient, ObjectMapper objectMapper) {
        this.blobServiceClient = blobServiceClient;
        this.objectMapper = objectMapper;
    }

    private BlobContainerClient getContainerClient(String containerName) {
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public Optional<FeatureToggle> findById(String fileName) {
        try {
            BlobContainerClient containerClient = getContainerClient(BLOB_CONTAINER_NAME);

            BlobClient blobClient = containerClient.getBlobClient(fileName + ".json");

            String jsonString = blobClient.downloadContent().toString();
            FeatureToggle featureToggle = objectMapper.readValue(jsonString, FeatureToggle.class);
            return Optional.of(featureToggle);
        } catch (Exception e) {
            log.error("An error occurred when getting the toggle");
            return Optional.empty();
        }
    }
}