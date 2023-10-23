package com.stitches.controller;

import com.stitches.dto.request.CountryDTO;
import com.stitches.dto.response.HttpResponse;
import com.stitches.model.Country;
import com.stitches.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService){
        this.countryService = countryService;
    }

    @PostMapping("/country")
    public ResponseEntity<HttpResponse> addCountry(@RequestBody CountryDTO countryDTO){
        CountryDTO countryDTO1 = countryService.addCountry(countryDTO);
        Map<String, CountryDTO> countryDTOMap = new HashMap<>();
        countryDTOMap.put("result",countryDTO1);

        HttpResponse httpResponse = HttpResponse.builder()
                .message("Country created successfully")
                .data(countryDTOMap)
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.ok(httpResponse);
    }

    @GetMapping("/countries")
    public ResponseEntity<HttpResponse> fectchCountryList(){

        Map<String, Optional<List<Country>>> countryList = new HashMap<>();
        Optional<List<Country>> countries = countryService.findAllCountries();
        countryList.put("result", countries);
        HttpResponse httpResponse = HttpResponse.builder()
                .message("successful")
                .status(HttpStatus.OK)
                .data(countryList)
                .statusCode(200)
                .timeStamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.ok(httpResponse);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<HttpResponse> getIca(@PathVariable String countryCode){
        Optional<Country> country = countryService.findCountryByCode(countryCode);

        Map<String, Optional<Country>> optionalMap = new HashMap<>();
        optionalMap.put("result", country);
        HttpResponse httpResponse;
        if(country.isPresent()) {
             httpResponse = HttpResponse.builder()
                    .data(optionalMap)
                    .message("Successful")
                    .timeStamp(LocalDateTime.now().toString())
                    .statusCode(201)
                    .status(HttpStatus.OK)
                    .build();
        }else{
            httpResponse = HttpResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(400)
                    .build();
        }
        return ResponseEntity.ok(httpResponse);
    }
}
