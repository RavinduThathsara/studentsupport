package com.studentsupport.controller;

import com.studentsupport.entity.Report;
import com.studentsupport.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ReportService reports;

    public AdminController(ReportService reports) {
        this.reports = reports;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> openReports() {
        return ResponseEntity.ok(reports.listOpen());
    }

    @PostMapping("/reports/{id}/resolve")
    public ResponseEntity<Report> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(reports.resolve(id));
    }
}
