package com.yourapp.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email @NotBlank
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}

{"fullName":"Test User","email":"test@gmail.com","password":"123456"}
