package com.stitches.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
