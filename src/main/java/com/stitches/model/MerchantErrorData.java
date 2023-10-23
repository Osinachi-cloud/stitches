package com.stitches.model;

import com.stitches.enums.RequestType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
@ToString
public class MerchantErrorData {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<MerchantErrorObj> error;
    private String ica;
    private String email;
    private String acquirerIdentifier;
    private LocalDate requestDate;
    private RequestType requestType;
    private String merchantId;
    private String merchantName;
    private String mccCode;
}
