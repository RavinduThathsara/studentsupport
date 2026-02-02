package com.studentsupport.dto.request;

import com.studentsupport.entity.enums.Category;
import com.studentsupport.entity.enums.RequestStatus;
import lombok.*;

import java.time.Instant;

@Getter @Builder
@AllArgsConstructor
public class SupportRequestResponseDTO {
    private Long id;
    private String title;
    private Category category;
    private String description;
    private RequestStatus status;

    private Long requesterId;
    private String requesterEmail;

    private Long helperId;       // nullable
    private String helperEmail;  // nullable

    private Instant createdAt;
}
