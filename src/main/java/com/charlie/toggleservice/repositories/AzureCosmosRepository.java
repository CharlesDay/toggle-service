package com.charlie.toggleservice.repositories;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.charlie.toggleservice.model.FeatureToggle;
import org.springframework.stereotype.Repository;

@Repository
public interface AzureCosmosRepository extends CosmosRepository<FeatureToggle, String> {
}
