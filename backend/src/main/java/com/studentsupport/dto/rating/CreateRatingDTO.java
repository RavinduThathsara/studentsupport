package com.studentsupport.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateRatingDTO {
    @NotNull
    @Min(1) @Max(5)
    private Integer stars;

    private String comment;
}
