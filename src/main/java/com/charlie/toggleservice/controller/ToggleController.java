package com.charlie.toggleservice.controller;

import com.charlie.toggleservice.model.FeatureToggle;
import com.charlie.toggleservice.model.FeatureToggleCreateRequest;
import com.charlie.toggleservice.services.ToggleService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping()
@Slf4j
public class ToggleController {
    @Autowired
    ToggleService toggleService;

    @GetMapping
    public ResponseEntity<?> getToggle(String name) {
        FeatureToggle toggleOptional = toggleService.findByName(name);
        if (toggleOptional != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no toggle matching that name");
        }

        return ResponseEntity.ok().body(toggleOptional);
    }


    @GetMapping("/toggle")
    public String toggleForm(Model model) {
        List<FeatureToggle> toggles = toggleService.findAll();
        model.addAttribute("toggles", toggles);
        return "toggle-form";
    }

    @PostMapping("/toggle")
    public String toggleSpecificToggle(@RequestParam("specificToggleName") String toggleName) {
        // Find the toggle with the specified name
        Optional<FeatureToggle> toggle = toggleService.getToggleById(toggleName);

        toggle.ifPresent(featureToggle -> toggleService.toggle(featureToggle));

        return "redirect:/toggle"; // Redirect back to the toggle form
    }

    @PostMapping("/deleteToggle")
    public String deleteToggle(@RequestParam("specificToggleNameToDelete") String specificToggleNameToDelete) {
        toggleService.deleteIfExists(specificToggleNameToDelete);

        return "redirect:/toggle"; // Redirect back to the toggle form
    }

@PostMapping("/createToggle")
    public String createNewToggle(@RequestParam("newToggleName") String toggleName, @Nullable @RequestParam("newToggleStatus") boolean active) {
        FeatureToggleCreateRequest featureToggleCreateRequest = new FeatureToggleCreateRequest(toggleName, active);
        toggleService.createToggle(featureToggleCreateRequest);
        return "redirect:/toggle"; // Redirect back to the toggle form
    }
}