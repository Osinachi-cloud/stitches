package com.stitches.dto.request;

import com.stitches.enums.Gender;
import com.stitches.enums.Role;
import com.stitches.model.Address;
import com.stitches.model.BodyMeasurement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {

    private String email;
    private String password;
    private Role role;
    private String city;
    private String street;
    private String state;
    private String country;
    private String mobile;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private Gender gender;
    private BodyMeasurement bodyMeasurement;
    private Address address;

}