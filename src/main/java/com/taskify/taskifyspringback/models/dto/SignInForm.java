package com.taskify.taskifyspringback.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInForm {

    @JsonProperty("email")
    @NotBlank(message = "email field is required")
    @Email(message = "the field must be an email")
    @Size(max = 100, message = "the email must have less than 100 characters")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "password field is required")
    @Size(min = 8, message = "the password must have at least 8 characters")
    private String password;
    
}
