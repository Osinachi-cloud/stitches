package com.stitches.controller;


import com.stitches.dto.request.BodyMeasurementRequest;
import com.stitches.dto.request.CountryDTO;
import com.stitches.dto.response.BodyMeasurementResponse;
import com.stitches.dto.response.HttpResponse;
import com.stitches.service.BodyMeasurementService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/body-measurement")
public class BodyMeasurementController {

    private final BodyMeasurementService bodyMeasurementService;

    public BodyMeasurementController(BodyMeasurementService bodyMeasurementService){
        this.bodyMeasurementService = bodyMeasurementService;
    };

    @PostMapping("")
    public ResponseEntity<HttpResponse> saveBodyMeasurement(@RequestBody @Valid BodyMeasurementRequest bodyMeasurementRequest, @RequestParam(name = "email", required = true) String customerEmail){
        BodyMeasurementResponse bodyMeasurementResponse = bodyMeasurementService.saveBodyMeasurement(bodyMeasurementRequest, customerEmail);
        Map<String, BodyMeasurementResponse> bodyMeasurementResponseMap = new HashMap<>();
        bodyMeasurementResponseMap.put("data",bodyMeasurementResponse);

        HttpResponse httpResponse = HttpResponse.builder()
                .message("Body measurement successfully saved")
                .data(bodyMeasurementResponseMap)
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.ok(httpResponse);
    }


//    @PostMapping("")
//    public ResponseEntity<HttpResponse> saveBodyMeasurement(@RequestBody @Valid BodyMeasurementRequest bodyMeasurementRequest, @RequestParam(name = "email", required = true) String customerEmail) {
//        try {
//            BodyMeasurementResponse bodyMeasurementResponse = bodyMeasurementService.saveBodyMeasurement(bodyMeasurementRequest, customerEmail);
//            Map<String, BodyMeasurementResponse> bodyMeasurementResponseMap = new HashMap<>();
//            bodyMeasurementResponseMap.put("data", bodyMeasurementResponse);
//
//            HttpResponse httpResponse = HttpResponse.builder()
//                    .message("Body measurement successfully saved")
//                    .data(bodyMeasurementResponseMap)
//                    .timeStamp(LocalDateTime.now().toString())
//                    .statusCode(201)
//                    .status(HttpStatus.CREATED)
//                    .build();
//
//            return ResponseEntity.ok(httpResponse);
//        } catch (Exception e) {
//
//            Map<String, String> responseMap = new HashMap<>();
//            responseMap.put("data", e.getMessage());
//            // Handle validation error and return a specific error response
//            HttpResponse errorResponse = HttpResponse.builder()
//                    .message("Validation failed")
//                    .data(responseMap)
//                    .timeStamp(LocalDateTime.now().toString())
//                    .statusCode(400) // Bad Request
//                    .status(HttpStatus.BAD_REQUEST)
//                    .build();
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//        }
//    }


}
