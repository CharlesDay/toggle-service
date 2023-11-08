package com.charlie.toggleservice.controller;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.repositories.ToggleRepository;
import com.charlie.toggleservice.services.ToggleService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping()
@Slf4j
public class ToggleController {

    @Autowired
    ToggleRepository toggleRepository;

    @Autowired
    ToggleService toggleService;

//    @PostMapping
//    public ResponseEntity<?> createToggle(@RequestBody FeatureToggleCreateRequest createRequest) {
//        FeatureToggle toggle = toggleService.createToggle(createRequest);
//
//        if (toggleService == null) {
//            return ResponseEntity.internalServerError().build();
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(toggle);
//    }
//
//    @PatchMapping("/{name}")
//    public ResponseEntity<?> toggle(@PathVariable String name) {
//        Optional<FeatureToggle> toggleById = toggleRepository.findById(name);
//        if (toggleById.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(toggleService.toggle(toggleById.get()));
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<?> getToggle(@PathVariable String name) {
//        Optional<FeatureToggle> toggleById = toggleRepository.findById(name);
//        if (toggleById.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().body(toggleById.get());
//    }
//
//    @DeleteMapping("/{name}")
//    public ResponseEntity<?> deleteToggle(@PathVariable String name) {
//        if (toggleRepository.existsById(name)) {
//            toggleRepository.deleteById(name);
//            return ResponseEntity.ok().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/toggle")
    public String toggleForm(Model model) {
        Iterable<FeatureToggle> toggles = toggleRepository.findAll();
        model.addAttribute("toggles", toggles);
        return "toggle-form";
    }

    @PostMapping("/toggle")
    public String toggleExistingToggles(@RequestParam("toggleNames") List<FeatureToggle> toggleNames) {
        // Toggle existing toggles based on the list of toggle names received
        toggleService.toggleExistingTogglesIfChanged(toggleNames);
        return "redirect:/toggle";
    }

    @PostMapping("/createToggle")
    public String createNewToggle(@ModelAttribute("featureToggle") FeatureToggle featureToggle) {
        FeatureToggleCreateRequest featureToggleCreateRequest = new FeatureToggleCreateRequest(featureToggle.getName(), featureToggle.isActive());
        // Create a new toggle in the backend based on the form submission
        toggleService.createToggle(featureToggleCreateRequest);
        return "redirect:/toggle"; // Redirect back to the toggle form
    }
}