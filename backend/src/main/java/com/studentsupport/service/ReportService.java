package com.studentsupport.service;

import com.studentsupport.dto.report.CreateReportDTO;
import com.studentsupport.entity.*;
import com.studentsupport.exception.NotFoundException;
import com.studentsupport.repository.*;
import com.studentsupport.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reports;
    private final SupportRequestRepository requests;
    private final UserRepository users;

    public ReportService(ReportRepository reports, SupportRequestRepository requests, UserRepository users) {
        this.reports = reports;
        this.requests = requests;
        this.users = users;
    }

    private User me() {
        return users.findByEmail(SecurityUtil.currentEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Report create(CreateReportDTO dto) {
        User reporter = me();
        SupportRequest r = requests.findById(dto.getRequestId())
                .orElseThrow(() -> new NotFoundException("Request not found"));

        Report report = Report.builder()
                .reporter(reporter)
                .request(r)
                .reason(dto.getReason())
                .status("OPEN")
                .createdAt(Instant.now())
                .build();

        return reports.save(report);
    }

    public List<Report> listOpen() {
        return reports.findByStatus("OPEN");
    }

    public Report resolve(Long reportId) {
        Report rep = reports.findById(reportId).orElseThrow(() -> new NotFoundException("Report not found"));
        rep.setStatus("RESOLVED");
        return reports.save(rep);
    }
}
