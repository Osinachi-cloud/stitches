package com.stitches.controller;

import com.stitches.dto.request.MerchantRequestDto;
import com.stitches.dto.request.MerchantUpdateRequestDto;
import com.stitches.dto.response.HttpResponse;
import com.stitches.dto.response.MerchantResponseDto;
import com.stitches.model.Merchant;
import com.stitches.repository.MerchantRepository;
import com.stitches.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
public class MerchantController {

    private final Logger log = LoggerFactory.getLogger(MerchantController.class);
    private final MerchantService merchantService;
    private final MerchantRepository merchantRepository;

    public MerchantController(MerchantService merchantOnBoardingService, MerchantRepository merchantRepository){
        this.merchantService = merchantOnBoardingService;
        this.merchantRepository = merchantRepository;
    }

//    @PreAuthorize("hasRole('ROLE_USER') and hasPermission('review')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/save-merchant")
    public ResponseEntity<MerchantResponseDto> addMerchant(@RequestBody MerchantRequestDto merchantRequestDto){
        MerchantResponseDto merchantResponseDto = merchantService.addMerchant(merchantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantResponseDto);
    }

    @GetMapping("/merchants/{merchantId}")
    public ResponseEntity<HttpResponse> getMerchant(@PathVariable Long merchantId){
        MerchantResponseDto merchantResponseDto = merchantService.findMerchantById(merchantId);
        Map<String, MerchantResponseDto> merchantRequestDtoMap = new HashMap<>();
        merchantRequestDtoMap.put("content", merchantResponseDto);

        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(200)
                .status(OK)
                .data(merchantRequestDtoMap)
                .message("Merchant retrieved")
                .build();
        return ResponseEntity.status(OK).body(httpResponse);
    }

    @GetMapping("/merchants")
    public ResponseEntity<HttpResponse> getMerchants(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String acquirerIdentifier,
            @RequestParam(required = false) String merchantID,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String cardAcceptorBusinessCode,
            @RequestParam(required = false) String requestDate,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws InterruptedException {

        Map<String, Object> responseData = new HashMap<>();
        log.info("merchants : {}", merchantService.getMerchants(status, acquirerIdentifier, merchantID, requestType, cardAcceptorBusinessCode, requestDate, page.orElse(0), size.orElse(10)));
        responseData.put("page", merchantService.getMerchants(status, acquirerIdentifier, merchantID, requestType, cardAcceptorBusinessCode, requestDate, page.orElse(0), size.orElse(10)));

        HttpResponse httpResponse = HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(responseData)  // Use the created Map here
                        .message("Merchants Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build();
        log.info("httpResponse : {}", httpResponse);
        return ResponseEntity.ok().body(httpResponse);
    }

    // just for test
    @GetMapping("/all")
    public ResponseEntity<List<Merchant>> allMerchants(@RequestParam(required = true, value = "ica") String ica){
        return ResponseEntity.ok(merchantService.getAllMerchantsByICa(ica));
    }

    @GetMapping("/get-merchants-by-status")
    public ResponseEntity<HttpResponse> getMerchantsByStatus(@RequestParam Optional<String> status,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) throws InterruptedException {
        log.info("status =====> {}",status);
//            TimeUnit.SECONDS.sleep(1);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("page", merchantService.getMerchantsByStatus(status.orElse(""), page.orElse(0), size.orElse(10)));

            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .data(responseData)  // Use the created Map here
                            .message("Merchants Retrieved")
                            .status(OK)
                            .statusCode(OK.value())
                            .build());
    }


    @DeleteMapping("/delete-merchant")
        public ResponseEntity<Void> deleteMerchant(@RequestParam("merchantId") Long merchantId) {
            merchantService.deleteMerchant(merchantId);
            return ResponseEntity.noContent().build();
        }


    @PreAuthorize("hasRole('ROLE_USER') and hasPermission('approve')")
    @PatchMapping("/merchants/{merchantId}")
    public ResponseEntity<HttpResponse> updateMerchant(@PathVariable Long merchantId, @RequestBody MerchantUpdateRequestDto merchantRequestDto){
        MerchantResponseDto merchantResponseDto = merchantService.updateMerchant(merchantId, merchantRequestDto);

        Map<String, MerchantResponseDto> merchantRequestDtoMap = new HashMap<>();
        merchantRequestDtoMap.put("content", merchantResponseDto);

        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(200)
                .status(OK)
                .data(merchantRequestDtoMap)
                .message("Merchant retrieved")
                .build();
        return ResponseEntity.ok().body(httpResponse);
    }

    @PostMapping("/hello")
    public ResponseEntity<String> helloEndpoint(@RequestHeader("Authorization") String token) {
        log.info("TOKEN {}", token);

        if(merchantService.validateToken(token)) {
            log.info("TOKEN {}", token);
            return ResponseEntity.ok("Hello from the protected endpoint!");
        } else{
            return ResponseEntity.status(401).body("Unauthorized: Invalid Token");
        }
    }

}



