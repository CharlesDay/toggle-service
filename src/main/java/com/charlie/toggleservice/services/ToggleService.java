package com.charlie.toggleservice.services;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.ToggleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (!doesToggleAlreadyExist) {
            FeatureToggle newFeatureToggle = FeatureToggle.builder().name(createRequest.name()).isActive(createRequest.active()).build();
            return toggleRepository.save(newFeatureToggle);        }
        return null;
    }

    public FeatureToggle toggle(FeatureToggle featureToggle) {
        featureToggle.toggle();
        return toggleRepository.save(featureToggle);
    }

    public void toggleExistingTogglesIfChanged(List<FeatureToggle> toggles) {
        for (FeatureToggle feature : toggles) {
            Optional<FeatureToggle> existingToggle = toggleRepository.findById(feature.getName());
            if (existingToggle.isPresent() && existingToggle.get().isActive() != feature.isActive()) {
                toggle(feature);
            }
        }
    }
}