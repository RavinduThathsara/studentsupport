package com.studentsupport.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMessageDTO {
    @NotBlank
    private String text;
}
