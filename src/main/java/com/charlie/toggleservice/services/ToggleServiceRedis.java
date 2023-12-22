package com.charlie.toggleservice.services;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.ToggleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ToggleServiceRedis implements ToggleService {

    //    @Autowired
    private ToggleRepository toggleRepository;

    @Autowired
    public ToggleServiceRedis(ToggleRepository toggleRepository) {
        this.toggleRepository = toggleRepository;

    }

    public Optional<FeatureToggle> getToggleById(String id) {
        return toggleRepository.findById(id);
    }

    public FeatureToggle createToggle(FeatureToggleCreateRequest createRequest) {
        boolean doesToggleAlreadyExist = toggleRepository.existsById(createRequest.name());

        if (!doesToggleAlreadyExist) {
            FeatureToggle newFeatureToggle = FeatureToggle.builder().name(createRequest.name()).isActive(createRequest.isActive()).build();
            return toggleRepository.save(newFeatureToggle);
        }
        return null;
    }

    public FeatureToggle toggle(FeatureToggle featureToggle) {
        featureToggle.toggle();
        return toggleRepository.save(featureToggle);
    }

    public FeatureToggle findByName(String name) {
        Optional<FeatureToggle> byId = toggleRepository.findById(name);
        return byId.orElse(null);
    }

    public List<FeatureToggle> findAll() {
        Iterable<FeatureToggle> all = toggleRepository.findAll();
        List<FeatureToggle> toggles = new ArrayList<>();
        all.forEach(toggles::add);
        return toggles;
    }

    public void deleteIfExists(String name) {
        if (toggleRepository.existsById(name)) {
            toggleRepository.deleteById(name);
        }
    }
}