package com.studentsupport.controller;

import com.studentsupport.dto.request.*;
import com.studentsupport.entity.enums.RequestStatus;
import com.studentsupport.service.SupportRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class SupportRequestController {

    private final SupportRequestService service;

    public SupportRequestController(SupportRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SupportRequestResponseDTO> create(@Valid @RequestBody CreateSupportRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<SupportRequestResponseDTO>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportRequestResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportRequestResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateSupportRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<SupportRequestResponseDTO> accept(@PathVariable Long id) {
        return ResponseEntity.ok(service.accept(id));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<SupportRequestResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status
    ) {
        return ResponseEntity.ok(service.changeStatus(id, status));
    }
}
