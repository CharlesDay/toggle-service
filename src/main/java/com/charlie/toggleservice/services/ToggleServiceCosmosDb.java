package com.charlie.toggleservice.services;

import com.azure.cosmos.models.PartitionKey;
import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.AzureCosmosRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToggleServiceCosmosDb implements ToggleService {

    private final AzureCosmosRepository repository;

    @Autowired
    public ToggleServiceCosmosDb(AzureCosmosRepository azureCosmosRepository) {
        this.repository = azureCosmosRepository;
    }

    @Override
    public Optional<FeatureToggle> getToggleById(String id) {
        return repository.findById(id, new PartitionKey(id));
    }

    @Override
    public FeatureToggle createToggle(FeatureToggleCreateRequest createRequest) {
        FeatureToggle newFeatureToggle = FeatureToggle.builder().name(createRequest.name()).isActive(createRequest.isActive()).build();
        return newFeatureToggle;
    }

    @Override
    public FeatureToggle toggle(FeatureToggle featureToggle) {
        return null;
    }

    @Override
    public FeatureToggle findByName(String name) {
        return repository.findById(name, new PartitionKey(name)).orElse(null);
    }

    @Override
    public List<FeatureToggle> findAll() {
        Iterable<FeatureToggle> featureToggles = repository.findAll(new PartitionKey("/name"));
        List<FeatureToggle> result = new ArrayList<>();
        featureToggles.forEach(result::add);
        return result;
    }

    @Override
    public void deleteIfExists(String name) {
        repository.deleteById(name, new PartitionKey(name));

    }
}
