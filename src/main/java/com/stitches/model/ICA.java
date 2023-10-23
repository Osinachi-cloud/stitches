package com.stitches.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

//    @OneToMany
//    @JoinColumn(name = "ica_id")
//    private List<Merchant> merchants;


//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "ica_id")
//    private Country country;
}
