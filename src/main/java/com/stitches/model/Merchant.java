package com.stitches.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.stitches.enums.FileDeliveryStatus;
import com.stitches.enums.RequestType;
import com.stitches.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@Builder
@ToString
public class Merchant implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String acquirerIdentifier;
    private String email;
    private String mobile;

    @Enumerated(EnumType.STRING)
    private FileDeliveryStatus fileDeliveryStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;
    private String merchantID;

//    @ToString.Exclude
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private String countryName;


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "ica_id", insertable = false, updatable = false)
    private String icaName;


    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    private String cardAcceptorBusinessCode;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

// countries, create.
// ICA's under each country.
// merchant unBoarding
// two roles, maker and checker
// capture, download, edit merchant: maker
// approve: checker
// status .
// endpoint should generate a file.
// status, if error state reason as field.
