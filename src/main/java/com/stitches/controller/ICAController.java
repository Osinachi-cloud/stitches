package com.stitches.controller;

import com.stitches.dto.request.ICADTO;
import com.stitches.dto.response.HttpResponse;
import com.stitches.service.ICAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class ICAController {

    private Logger log = LoggerFactory.getLogger(ICAController.class);

    private final ICAService icaService;

    public ICAController(ICAService icaService){
        this.icaService = icaService;
    }

    @PostMapping("/ica")
    public ResponseEntity<HttpResponse> addICAs(@RequestBody List<ICADTO> icaList, @RequestParam (required = true, name = "countryCode") String countryCode){
        List<ICADTO> icaDTOList = icaService.addICA(icaList, countryCode);
        log.info("This is the controller ICA list {}", icaDTOList);
        Map<String, List<ICADTO>> resultMap = new HashMap<>();
        resultMap.put("result", icaDTOList);
        HttpResponse httpResponse = HttpResponse.builder()
                .data(resultMap)
                .message("Successfully added")
                .status(HttpStatus.CREATED)
                .statusCode(201)
                .timeStamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.ok(httpResponse);
    }
}
