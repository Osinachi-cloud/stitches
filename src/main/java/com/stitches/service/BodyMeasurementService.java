package com.stitches.service;

import com.stitches.dto.request.BodyMeasurementRequest;
import com.stitches.dto.response.BodyMeasurementResponse;
import com.stitches.model.BodyMeasurement;

public interface BodyMeasurementService {

    BodyMeasurementResponse saveBodyMeasurement(BodyMeasurementRequest request, String customerEmail);


}
