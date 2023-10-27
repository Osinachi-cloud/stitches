package com.stitches.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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


    @NotNull(message = "Neck length is required")
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private Integer neck;
    private int shoulder;
    private int chest;
    private int tummy;
    private int hipWidth;
    private int neckToHipLength;
    private int shortSleeveAtBiceps;
    private int midSleeveAtElbow;
    private int longSleeveAtWrist;
    private int waist;
    private int thigh;
    private int knee;
    private int ankle;
    private int trouserLength;
}
