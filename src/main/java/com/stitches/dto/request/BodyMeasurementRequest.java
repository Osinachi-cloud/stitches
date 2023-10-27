package com.stitches.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BodyMeasurementRequest {

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int neck;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int shoulder;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int chest;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int tummy;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int hipWidth;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int neckToHipLength;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int shortSleeveAtBiceps;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int midSleeveAtElbow;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int longSleeveAtWrist;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int waist;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int thigh;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int knee;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int ankle;

    @NotNull(message = "Neck length is required")
    @Positive
    @Min(value=1, message="neck length: positive number, min 18 is required")
    @Max(value=100, message="neck length: positive number, max 100 is required")
    private int trouserLength;
}