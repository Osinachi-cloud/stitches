package com.stitches.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPasswordRequest{
    private String password;
    private String confirmPassword;

}