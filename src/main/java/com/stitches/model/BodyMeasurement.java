package com.stitches.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "body_measurement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String neck;
    private String shoulder;
    private String chest;
    private String tummy;
    private String hipWidth;
    private String neckToHipLength;
    private String shortSleeveAtBiceps;
    private String midSleeveAtElbow;
    private String longSleeveAtWrist;
    private String waist;
    private String thigh;
    private String knee;
    private String ankle;
    private String trouserLength;
}
