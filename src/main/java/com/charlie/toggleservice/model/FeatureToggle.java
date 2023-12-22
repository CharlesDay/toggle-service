package com.charlie.toggleservice.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.charlie.toggleservice.config.FeatureToggleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.lang.annotation.Documented;

@Cacheable("Toggle")
@RedisHash("Toggles")
@Builder
@Getter
@Document(collection = "toggles")
@JsonSerialize(using = FeatureToggleSerializer.class)
public class FeatureToggle implements Serializable {


    @Id
    @PartitionKey
    private final String name;
    private boolean isActive;

    public void toggle() {
        isActive = !isActive;
    }
}