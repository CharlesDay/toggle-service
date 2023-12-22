package com.charlie.toggleservice.services;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.AzureBlobRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ToggleServiceAzureBlob implements ToggleService {

    private AzureBlobRepository repository;

    @Autowired
    public ToggleServiceAzureBlob(AzureBlobRepository repository) {
        this.repository = repository;

    }

    @Override
    public Optional<FeatureToggle> getToggleById(String id) {
        return repository.findById(id);
    }

    @Override
    public FeatureToggle createToggle(FeatureToggleCreateRequest createRequest) {
        return null;
    }

    @Override
    public FeatureToggle toggle(FeatureToggle featureToggle) {
        return null;
    }

    @Override
    public FeatureToggle findByName(String name) {
        Optional<FeatureToggle> byId = repository.findById(name);
        return byId.orElse(null);
    }

    @Override
    public List<FeatureToggle> findAll() {
        return null;
    }

    @Override
    public void deleteIfExists(String name) {

    }
}
