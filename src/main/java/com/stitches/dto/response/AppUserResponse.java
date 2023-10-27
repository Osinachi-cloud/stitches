package com.stitches.dto.response;

import com.stitches.enums.Gender;
import com.stitches.enums.Role;
import com.stitches.model.Address;
import com.stitches.model.BodyMeasurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {
    private String email;
    private Role role;
    private String mobile;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private Gender gender;
    private BodyMeasurement bodyMeasurement;
    private Address address;

}