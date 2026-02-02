package com.studentsupport.controller;

import com.studentsupport.dto.report.CreateReportDTO;
import com.studentsupport.entity.Report;
import com.studentsupport.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Report> create(@Valid @RequestBody CreateReportDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }
}
