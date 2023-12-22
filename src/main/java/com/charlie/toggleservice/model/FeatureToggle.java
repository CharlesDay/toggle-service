package com.charlie.toggleservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Serializable;

@Cacheable("Toggle")
@RedisHash("Toggles")
@Builder
@Getter
public class FeatureToggle implements Serializable {


    @Id
    private final String name;
    @JsonProperty("isActive")
    private boolean isActive;

    public void toggle() {
        isActive = !isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}