package com.stitches.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @NotNull
    @Size(min = 4, max = 20)
    private String email;

//    @NotBlank(message = "Password is required")
    @NotNull
    @Size(min = 4, max = 20)
    private String password;
}
