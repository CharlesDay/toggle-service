package com.charlie.toggleservice.services;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;

import java.util.List;
import java.util.Optional;

public interface ToggleService {
    Optional<FeatureToggle> getToggleById(String id);

    FeatureToggle createToggle(FeatureToggleCreateRequest createRequest);

    FeatureToggle toggle(FeatureToggle featureToggle);

    FeatureToggle findByName(String name);

    List<FeatureToggle> findAll();

    void deleteIfExists(String name);

}
