package com.charlie.toggleservice.controller;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.ToggleRepository;
import com.charlie.toggleservice.services.ToggleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping()
@Slf4j
public class ToggleController {

    @Autowired
    ToggleRepository toggleRepository;

    @Autowired
    ToggleService toggleService;

    @PostMapping
    public ResponseEntity<?> createToggle(@RequestBody FeatureToggleCreateRequest createRequest) {
        FeatureToggle toggle = toggleService.createToggle(createRequest);

        if (toggleService == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(toggle);
    }

    @PatchMapping("/{name}")
    public ResponseEntity<?> toggle(@PathVariable String name) {
        Optional<FeatureToggle> toggleById = toggleRepository.findById(name);
        if (toggleById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(toggleService.toggle(toggleById.get()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getToggle(@PathVariable String name) {
        Optional<FeatureToggle> toggleById = toggleRepository.findById(name);
        if (toggleById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(toggleById.get());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteToggle(@PathVariable String name) {
        if (toggleRepository.existsById(name)) {
            toggleRepository.deleteById(name);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}