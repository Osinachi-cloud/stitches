package com.stitches.controller;

import com.stitches.dto.response.HttpResponse;
import com.stitches.exceptions.ApiRequestException;
//import com.stitches.model.Merchant;
import com.stitches.model.Merchant;
import com.stitches.model.MerchantErrorData;
import com.stitches.model.MerchantErrorObj;
import com.stitches.repository.MerchantErrorDataRepository;
import com.stitches.repository.MerchantErrorRepository;
import com.stitches.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/api/vi/merchant-error")
public class MerchantErrorDataController {

    private Logger log = LoggerFactory.getLogger(MerchantController.class);
    private MerchantErrorDataRepository merchantErrorDataRepository;
    private MerchantErrorRepository merchantErrorRepository;
    private MerchantRepository merchantRepository;

    public MerchantErrorDataController(MerchantErrorDataRepository merchantErrorDataRepository, MerchantErrorRepository merchantErrorRepository, MerchantRepository merchantRepository){
        this.merchantErrorDataRepository = merchantErrorDataRepository;
        this.merchantErrorRepository = merchantErrorRepository;
        this.merchantRepository = merchantRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<HttpResponse> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("T626_file 1 :{}",file);

        HttpResponse httpResponse = new HttpResponse();
        Map<String, String> mapRes = new HashMap<>();

        if (!file.isEmpty()){
            log.info("T626_file 2 :{}",file);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                List<MerchantErrorData> dataList = new ArrayList<>();
                List<MerchantErrorObj> merchantErrorList = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    // Process each line as per your requirements
                    if (line.length() >= 45) {
//                        String dateStr = line.substring(1, 9);
//                        LocalDate requestDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("ddMMyyyy"));
                        String acquirerIdentifier = line.substring(1, 12).trim();
                        String merchantId = line.substring(36,51).trim();
                        String errorCodes = line.substring(85);

                        log.info("errorCodes : {}", errorCodes);
                        System.out.println( "ans res " +acquirerIdentifier + " " + merchantId + " " + " " + errorCodes);

                        for(int i = 0; i < errorCodes.length() - 1; i =  i + 3){
                            String errCode = errorCodes.substring(i , i + 3);
                            MerchantErrorObj merchantError = new MerchantErrorObj(errCode);
                            log.info("merchantError :{}", merchantError);
                            merchantErrorList.add(merchantError);
                            merchantErrorRepository.save(merchantError);
                        }
                        Optional<Merchant> merchant =  merchantRepository.findMerchantByMerchantID(merchantId);
                        log.info("merchant : {}", merchant);
                        if(!merchant.isPresent()){
                            System.out.println("Not present");
                            throw new ApiRequestException("Merchant not found");
                        }else{
                            System.out.println("present");
                            MerchantErrorData merchantErrorData = new MerchantErrorData();


                            Merchant merchant1 = merchant.get();
                            log.info("merchant 1 ===>>> : {}", merchant1);
                            merchantErrorData.setId(merchant1.getId());
                            merchantErrorData.setEmail(merchant1.getEmail());
                            merchantErrorData.setMccCode(merchant1.getCardAcceptorBusinessCode());
                            merchantErrorData.setMerchantId(merchant1.getMerchantID());
                            merchantErrorData.setMerchantName(merchant1.getName());
                            merchantErrorData.setAcquirerIdentifier(acquirerIdentifier);
                            dataList.add(merchantErrorData);
                            merchantErrorDataRepository
                                    .save(merchantErrorData);

                        }
                    }
                }

                merchantErrorDataRepository.saveAll(dataList);
//                log.info("list of error : {}", merchantErrorData);
            } catch (Exception e) {
                e.printStackTrace();
                mapRes.put("data","Error processing and saving data.");
                httpResponse.setStatusCode(400);
                httpResponse.setStatus(HttpStatus.BAD_REQUEST);
                httpResponse.setData(mapRes);
                return ResponseEntity.ofNullable(httpResponse);
            }
        } else {
            mapRes.put("data","File is empty.");
            httpResponse.setData(mapRes);
            httpResponse.setStatusCode(400);
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.ofNullable(httpResponse);
        }

        mapRes.put("data","File successfully uploaded");
        httpResponse.setData(mapRes);
        httpResponse.setStatusCode(200);
        httpResponse.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(httpResponse);
    }

}
