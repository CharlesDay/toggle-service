package com.charlie.toggleservice.repositories;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.charlie.toggleservice.model.FeatureToggle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class AzureBlobRepository {

    private final BlobServiceClient blobServiceClient;
    private final ObjectMapper objectMapper;
    private static final String BLOB_CONTAINER_NAME = "toggles";
    private final BlobContainerClient blobContainerClient;

    @Autowired
    public AzureBlobRepository(BlobServiceClient blobServiceClient, ObjectMapper objectMapper) {
        this.blobServiceClient = blobServiceClient;
        this.objectMapper = objectMapper;
        this.blobContainerClient = getContainerClient(BLOB_CONTAINER_NAME);
    }

    private BlobContainerClient getContainerClient(String containerName) {
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public Optional<FeatureToggle> findById(String fileName) {
        try {

            BlobClient blobClient = blobContainerClient.getBlobClient(fileName + ".json");

            String jsonString = blobClient.downloadContent().toString();
            FeatureToggle featureToggle = objectMapper.readValue(jsonString, FeatureToggle.class);
            return Optional.of(featureToggle);
        } catch (Exception e) {
            log.error("An error occurred when getting the toggle");
            return Optional.empty();
        }
    }

    public FeatureToggle save(FeatureToggle featureToggle) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(featureToggle.getName() + ".json");

            byte[] data = objectMapper.writeValueAsString(featureToggle).getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(data);
            blobClient.deleteIfExists();
            blobClient.upload(inputStream, data.length);
            return featureToggle;
        } catch (Exception ex) {
            log.error("Could not save");
            return null;
        }
    }

    public FeatureToggle saveIfNotExist(FeatureToggle featureToggle) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(featureToggle.getName() + ".json");

            if (blobClient.exists()) {
                return null;
            }
            byte[] data = objectMapper.writeValueAsString(featureToggle).getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(data);
            System.out.println(new String(data));
            blobClient.deleteIfExists();
            blobClient.upload(inputStream, data.length);
            return featureToggle;
        } catch (Exception ex) {
            log.error("Could not save");
            return null;
        }
    }

    public List<FeatureToggle> findAll() {
        return getAllBlobJson(blobContainerClient).stream().map(item -> {
            try {
                return objectMapper.readValue(item, FeatureToggle.class);
            } catch (JsonProcessingException e) {
                log.error("Error converting blob to toggle");
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void deleteIfExists(String name) {
        BlobClient blobClient = blobContainerClient.getBlobClient(name + ".json");
        blobClient.deleteIfExists();
    }

    private static Set<String> getAllBlobJson(BlobContainerClient blobContainerClient) {
        Set<String> jsonSet = new HashSet<>();

        blobContainerClient.listBlobs().forEach(blobItem -> {
            String blobContent = downloadBlobContent(blobContainerClient.getBlobClient(blobItem.getName()));
            if (blobContent != null) {
                jsonSet.add(blobContent);
            }
        });

        return jsonSet;
    }

    private static String downloadBlobContent(com.azure.storage.blob.BlobClient blobClient) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(blobClient.openInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            log.error("Issue downloading the blob");
            return null;
        }
    }
}