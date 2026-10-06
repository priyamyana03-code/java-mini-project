package com.artisthub.controller;

import com.artisthub.entity.ArtClass;
import com.artisthub.service.ArtClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ArtClassController {

    private final ArtClassService artClassService;

    @Autowired
    public ArtClassController(ArtClassService artClassService) {
        this.artClassService = artClassService;
    }

    @GetMapping
    public ResponseEntity<List<ArtClass>> getClasses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String mode,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Double maxFee) {

        List<ArtClass> classes = artClassService.getClasses(category, level, mode, search, maxFee);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(@PathVariable Long id) {
        return artClassService.getClassById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Art class not found with ID: " + id));
    }
}
