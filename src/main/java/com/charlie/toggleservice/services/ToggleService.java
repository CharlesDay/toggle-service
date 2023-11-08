package com.charlie.toggleservice.services;

import com.charlie.toggleservice.exceptions.FeatureAlreadyExistsException;
import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.ToggleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ToggleService {

    @Autowired
    private ToggleRepository toggleRepository;


    public Optional<FeatureToggle> getToggleById(String id) {
        return toggleRepository.findById(id);
    }

    public FeatureToggle createToggle(FeatureToggleCreateRequest createRequest) {
        boolean doesToggleAlreadyExist = toggleRepository.existsById(createRequest.name());

        if (doesToggleAlreadyExist) {
            throw new FeatureAlreadyExistsException(String.format("The Feature %s already exists", createRequest.name()));
        }

        FeatureToggle newFeatureToggle = FeatureToggle.builder().name(createRequest.name()).isActive(createRequest.active()).build();
        return toggleRepository.save(newFeatureToggle);
    }

    public FeatureToggle toggle(FeatureToggle featureToggle) {
        featureToggle.toggle();
        return toggleRepository.save(featureToggle);
    }
}