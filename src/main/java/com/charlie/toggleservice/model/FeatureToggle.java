package com.charlie.toggleservice.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Cacheable(value = "Toggle")
@RedisHash("Toggles")
@Builder
@Getter
public class FeatureToggle implements Serializable {


    @Id
    private final String name;
    private boolean isActive;

    public void toggle() {
        isActive = !isActive;
    }
}