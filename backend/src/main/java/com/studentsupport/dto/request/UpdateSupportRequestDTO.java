package com.studentsupport.dto.request;

import com.studentsupport.entity.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSupportRequestDTO {
    @NotBlank
    private String title;

    @NotNull
    private Category category;

    @NotBlank
    private String description;
}
