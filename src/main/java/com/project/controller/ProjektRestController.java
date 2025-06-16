package com.project.controller;

import com.project.model.Projekt;
import com.project.service.ProjektService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjektRestController {

    private final ProjektService projektService;

    @Autowired
    public ProjektRestController(ProjektService projektService) {
        this.projektService = projektService;
    }

    @GetMapping("/projekty")
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektService.getProjekty(pageable);
    }

    @GetMapping("/projekty/{projektId}")
    public ResponseEntity<Projekt> getProjekt(@PathVariable Integer projektId) {
        Optional<Projekt> projekt = projektService.getProjekt(projektId);
        return projekt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/projekty")
    public ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {
        Projekt createdProjekt = projektService.setProjekt(projekt);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{projektId}")
                .buildAndExpand(createdProjekt.getProjektId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/projekty/{projektId}")
    public ResponseEntity<Projekt> updateProjekt(
            @PathVariable Integer projektId,
            @Valid @RequestBody Projekt projekt) {
        Optional<Projekt> existingProjekt = projektService.getProjekt(projektId);
        if (existingProjekt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        projekt.setProjektId(projektId);
        projektService.setProjekt(projekt);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/projekty/{projektId}")
    public ResponseEntity<Void> deleteProjekt(@PathVariable Integer projektId) {
        Optional<Projekt> projekt = projektService.getProjekt(projektId);
        if (projekt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        projektService.deleteProjekt(projektId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projekty/search")
    public Page<Projekt> searchProjekty(@RequestParam String nazwa, Pageable pageable) {
        return projektService.searchByNazwa(nazwa, pageable);
    }
}