package com.charlie.toggleservice.model;

import com.charlie.toggleservice.config.FeatureToggleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Cacheable("Toggle")
@RedisHash("Toggles")
@Builder
@Getter
@JsonSerialize(using = FeatureToggleSerializer.class)
public class FeatureToggle implements Serializable {


    @Id
    private final String name;
    private boolean isActive;

    public void toggle() {
        isActive = !isActive;
    }
}