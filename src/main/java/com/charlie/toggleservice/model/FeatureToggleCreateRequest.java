package com.charlie.toggleservice.model;

import jakarta.annotation.Nullable;

public record FeatureToggleCreateRequest(String name, @Nullable boolean active){};
