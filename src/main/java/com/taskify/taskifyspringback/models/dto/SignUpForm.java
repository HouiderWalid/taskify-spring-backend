package com.taskify.taskifyspringback.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpForm {

    @JsonProperty("email")
    @NotBlank(message = "email field is required")
    @Email(message = "the field must be an email")
    @Size(max = 100, message = "the email must have less than 100 characters")
    private String email;

    @NotBlank(message = "full name field is required")
    @JsonProperty("full_name")
    @Size(max = 100, message = "the full name must have less than 100 characters")
    private String fullName;

    @JsonProperty("password")
    @NotBlank(message = "password field is required")
    @Size(min = 8, message = "the password must have at least 8 characters")
    private String password;

}
