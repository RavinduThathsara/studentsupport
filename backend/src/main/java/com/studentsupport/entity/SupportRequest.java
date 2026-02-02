package com.studentsupport.entity;

import com.studentsupport.entity.enums.Category;
import com.studentsupport.entity.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "support_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SupportRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // requester (owner)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    // helper (nullable until accepted)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helper_id")
    private User helper;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Column(nullable = false)
    private Instant createdAt;
}
