package com.studentsupport.repository;

import com.studentsupport.entity.Message;
import com.studentsupport.entity.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRequestOrderByCreatedAtAsc(SupportRequest request);
}
