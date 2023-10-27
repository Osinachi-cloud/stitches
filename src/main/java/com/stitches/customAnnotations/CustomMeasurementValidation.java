package com.stitches.customAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface CustomMeasurementValidation {
    String message() default "Invalid measurement";

    String notBlankMessage() default "Measurement is required";

    String sizeMessage() default "Measurement: positive number, min 18 and max 100 is required";

    String minMessage() default "Measurement: positive number, min 18 is required";

    String maxMessage() default "Measurement: positive number, max 100 is required";

    @NotNull(message = "{CustomMeasurementValidation.notBlankMessage}")
    @Size(min = 1, max = 100, message = "{CustomMeasurementValidation.sizeMessage}")
    @Min(value = 18, message = "{CustomMeasurementValidation.minMessage}")
    @Max(value = 100, message = "{CustomMeasurementValidation.maxMessage}")
    @Positive(message = "must be a positive number ")
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
