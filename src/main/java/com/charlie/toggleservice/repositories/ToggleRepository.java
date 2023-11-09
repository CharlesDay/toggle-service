package com.charlie.toggleservice.repositories;

import com.charlie.toggleservice.model.FeatureToggle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToggleRepository extends CrudRepository<FeatureToggle, String> {
}