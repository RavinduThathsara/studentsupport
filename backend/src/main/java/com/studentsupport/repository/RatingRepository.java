package com.studentsupport.repository;

import com.studentsupport.entity.Rating;
import com.studentsupport.entity.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByRequest(SupportRequest request);
}
