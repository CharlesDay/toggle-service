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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping()
@Slf4j
public class ToggleController {
    ToggleService toggleService;

    @Autowired
    public ToggleController(ToggleService toggleService) {
        this.toggleService = toggleService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getToggle(@PathVariable("name") String name) {
        FeatureToggle toggle = toggleService.findByName(name);
        if (toggle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no toggle matching that name");
        }

        return ResponseEntity.ok().body(toggle);
    }

    @GetMapping("/")
    public ResponseEntity<?> getBase() {
        return ResponseEntity.ok(

        ).body("Hello");
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
        if (StringUtils.hasText(toggleName)) {
            FeatureToggleCreateRequest featureToggleCreateRequest = new FeatureToggleCreateRequest(toggleName, active);
            toggleService.createToggle(featureToggleCreateRequest);
        }
        return "redirect:/toggle"; // Redirect back to the toggle form
    }
}