package com.studentsupport.controller;

import com.studentsupport.dto.rating.CreateRatingDTO;
import com.studentsupport.entity.Rating;
import com.studentsupport.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests/{requestId}/rating")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Rating> rate(@PathVariable Long requestId, @Valid @RequestBody CreateRatingDTO dto) {
        return ResponseEntity.ok(service.rate(requestId, dto));
    }
}
