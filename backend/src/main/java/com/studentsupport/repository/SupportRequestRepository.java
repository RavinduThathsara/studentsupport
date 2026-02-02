package com.studentsupport.repository;

import com.studentsupport.entity.SupportRequest;
import com.studentsupport.entity.enums.Category;
import com.studentsupport.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Long> {

    List<SupportRequest> findByStatus(RequestStatus status);

    List<SupportRequest> findByCategory(Category category);
}
