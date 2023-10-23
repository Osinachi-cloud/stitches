package com.stitches.dto.request;

import com.stitches.enums.Role;
import com.stitches.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
//    private AddressDTO address;
    private String city;
    private String street;
    private String state;
    private String country;
    private String mobile;
    private LocalDate dateOfBirth;
}