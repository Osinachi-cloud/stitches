package com.stitches.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyMeasurementResponse {
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
