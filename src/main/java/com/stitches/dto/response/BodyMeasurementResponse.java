package com.stitches.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BodyMeasurementResponse {
    private int neck;
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
