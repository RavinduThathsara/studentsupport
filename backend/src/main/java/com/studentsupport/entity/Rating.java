package com.studentsupport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "ratings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Rating {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private SupportRequest request;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_id")
    private User rater;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ratee_id")
    private User ratee;

    @Column(nullable = false)
    private Integer stars; // 1..5

    @Column(length = 2000)
    private String comment;

    @Column(nullable = false)
    private Instant createdAt;
}
