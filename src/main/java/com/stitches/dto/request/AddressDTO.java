package com.stitches.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stitches.model.AppUser;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class AddressDTO {
    private String city;
    private String street;
    private String state;
    private String country;
}