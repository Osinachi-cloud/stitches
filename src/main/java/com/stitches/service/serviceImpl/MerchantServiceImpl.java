package com.stitches.service.serviceImpl;

import com.stitches.dto.request.MerchantRequestDto;
import com.stitches.dto.request.MerchantUpdateRequestDto;
import com.stitches.dto.response.MerchantResponseDto;
import com.stitches.enums.FileDeliveryStatus;
import com.stitches.enums.Status;
import com.stitches.exceptions.ApiRequestException;
import com.stitches.model.Country;
import com.stitches.model.ICA;
import com.stitches.model.Merchant;
import com.stitches.repository.CountryRepository;
import com.stitches.repository.ICARepository;
import com.stitches.repository.MerchantRepository;
import com.stitches.service.MerchantService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);
    private final String secretKey = "code";

    private MerchantRepository merchantRepository;
    private CountryRepository countryRepository;
    private ICARepository icaRepository;


    public MerchantServiceImpl(MerchantRepository merchantOnBoardingRepository,
                               CountryRepository countryRepository,
                               ICARepository icaRepository){
        this.merchantRepository = merchantOnBoardingRepository;
        this.countryRepository = countryRepository;
        this.icaRepository = icaRepository;
    }

    private Merchant getMerchantById(long id){
        Merchant merchant = merchantRepository.findById(id)
                .orElse(null);
        if(merchant != null){
            return merchant;
        } else {
            throw new ApiRequestException("Merchant id" + " " + id + " " + "could not be found");
        }
    }

    @Override
    public MerchantResponseDto findMerchantById(long id) {
        log.info("id ======> {}",  id);
        Merchant merchant = getMerchantById(id);
        log.info("merchant ======> {}",  merchant);

        MerchantResponseDto merchantResponseDto = convertToResponseDto(merchant);
        return merchantResponseDto;
    }

    private MerchantResponseDto convertToResponseDto(Merchant merchant) {
        MerchantResponseDto merchantResponseDto =  new MerchantResponseDto();
        merchantResponseDto.setIcaName(merchant.getIcaName());
        merchantResponseDto.setMerchantID(merchant.getMerchantID());
        merchantResponseDto.setMobile(merchant.getMobile());
        merchantResponseDto.setAcquirerIdentifier(merchant.getAcquirerIdentifier());
        merchantResponseDto.setLastUpdated(merchant.getLastUpdated());
        merchantResponseDto.setCardAcceptorBusinessCode(merchant.getCardAcceptorBusinessCode());
        merchantResponseDto.setRequestDate(merchant.getRequestDate());
        merchantResponseDto.setRequestType(merchant.getRequestType());
        merchantResponseDto.setStatus(merchant.getStatus());
        merchantResponseDto.setEmail(merchant.getEmail());
        merchantResponseDto.setCountryName(merchant.getCountryName());
        return merchantResponseDto;
    }

    @Override
    public Page<Merchant> getMerchants(
                                       String status,
                                       String acquirerIdentifier,
                                       String merchantID,
                                       String  requestType,
                                       String cardAcceptorBusinessCode,
                                       String  requestDate,
                                       int page,
                                       int size
    ) {
        log.info("Fetching users for page {} of size {}", page, size);
        log.info("merchants in service class {}", merchantRepository.findAll(of(page, size)));
        return merchantRepository.findAllBy(status, acquirerIdentifier, merchantID, requestType, cardAcceptorBusinessCode,requestDate, of(page, size));
    }




    @Scheduled(fixedRate = 60000)
    private void exportToTextFile() {

        SimpleDateFormat formatDate = new SimpleDateFormat("DDMMYYYY");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Group merchants by acquirerIdentifier and filters by delivery status, sends only unsent file.
        Map<String, List<Merchant>> groupedMerchants = getAllMerchants().stream()
                .filter(merchant -> merchant.getFileDeliveryStatus() == FileDeliveryStatus.NOT_SENT)
                .collect(Collectors.groupingBy(Merchant::getAcquirerIdentifier));

        // get the length of the list for
        int arrLength = groupedMerchants.entrySet().size();

        // Iterate over each group and write to separate files
        for (Map.Entry<String, List<Merchant>> entry : groupedMerchants.entrySet()){
            String acquirerIdentifier = entry.getKey();
            List<Merchant> merchants = entry.getValue();

            // Create a file for each group based on acquirerIdentifier
            String fileName = acquirerIdentifier + " _" + formatDate.format(new Date().getTime()) + "_merchant_data.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                // Write header and data for this group to the file
                String headerIdentifier = "1";
                String dateFileReceived = formatDate.format(new Date());
                String filler = "              ";
                String fillerSpaces = "                                              ";

                writer.write(headerIdentifier + acquirerIdentifier + dateFileReceived + filler + fillerSpaces);
                writer.newLine(); // Move to the next line

                DateTimeFormatter groupDateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
                for (Merchant merchant : merchants) {
                    String formattedDate = groupDateFormat.format(merchant.getRequestDate());
                    String line = String.format("%s%s %s %s %s %s %s ",
                            "2",
                            formattedDate,
                            merchant.getAcquirerIdentifier(),
                            merchant.getRequestType(),
                            merchant.getMerchantID(),
                            merchant.getName(),
                            merchant.getCardAcceptorBusinessCode()
                    );
                    writer.write(line);
                    writer.newLine(); // Move to the next line

                    MerchantUpdateRequestDto merchantUpdateRequestDto = new MerchantUpdateRequestDto();
                    merchantUpdateRequestDto.setFileDeliveryStatus(FileDeliveryStatus.SENT);
                    updateMerchant(merchant.getId(), merchantUpdateRequestDto);
                }

                String trailerIdentifier = "9";
                String recordCount = padWithZeros(arrLength, 9);
                String fillerCommonTrailerInfo = "        ";
                String fillerTrailerSpaces = "                                                    ";

                writer.write(trailerIdentifier + acquirerIdentifier + recordCount + fillerCommonTrailerInfo + fillerTrailerSpaces);
            } catch (IOException e) {
                log.info("error : {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String padWithZeros(int digit, int length){
        String digitStr = String.valueOf(digit);

        // Check if padding is needed
        if (digitStr.length() >= length) {
            return digitStr; // No padding needed
        }
        StringBuilder padded = new StringBuilder();

        // Calculate the number of zeros needed
        int zerosToAdd = length - digitStr.length();
        for (int i = 0; i < zerosToAdd; i++) {
            padded.append('0');
        }
        // Append the original digit
        padded.append(digitStr);
        return padded.toString();
    }

    @Override
    public List<Merchant> getAllMerchantsByICa(String ica){
        List<Merchant> merchantList = merchantRepository.findMerchantsByAcquirerIdentifier(ica);
        log.info("merchants in service class {}", merchantList);
        exportToTextFile();
        return merchantList;
    }

    @Override
    public List<Merchant> getAllMerchants(){
        List<Merchant> merchantList = merchantRepository.findAll();
        log.info("merchants in service class {}", merchantList);
        return merchantList;
    }

    @Override
    public Page<Merchant> getMerchantsByStatus(String status, int page, int size) {
        log.info("Fetching users for page {} of size {}", page, size, status);
        if(status == null){
            throw new ApiRequestException("status can not be null") ;
        }

        return merchantRepository.findMerchantsByStatusContaining(status, of(page, size));
    }

    private Country findCountryByCountryCode(String code){
        log.info("code {}: ", code);
        Optional<Country> country = countryRepository.findCountryByCode(code);
        if(!country.isPresent()){
           throw new ApiRequestException("Country " + code + " not found");
        }
        else{
            return country.get();

        }
    }
    private ICA findICAByName(Long id){
        Optional<ICA> ica = icaRepository.findById(id);
        if(!ica.isPresent()){
            throw new ApiRequestException("ICA not found");
        }else{
            return ica.get();
        }
    }

    @Override
    public MerchantResponseDto addMerchant(MerchantRequestDto merchantRequestDto) {
        log.info("merchantRequestDto : {}", merchantRequestDto);
        Country country = findCountryByCountryCode(merchantRequestDto.getCountryCode());
//        if(country)
        ICA ica = findICAByName(merchantRequestDto.getIcaId());
        log.info("country {}", country);


        Merchant merchant = Merchant.builder()
                .acquirerIdentifier(merchantRequestDto.getAcquirerIdentifier())
                .cardAcceptorBusinessCode(merchantRequestDto.getCardAcceptorBusinessCode())
                .requestDate(LocalDateTime.now())
                .status(Status.Pending)
                .merchantID(merchantRequestDto.getMerchantID())
                .email(merchantRequestDto.getEmail())
                .mobile(merchantRequestDto.getMobile())
                .lastUpdated(LocalDateTime.now())
                .requestType(merchantRequestDto.getRequestType())
                .countryName(country.getName())
                .icaName(ica.getName())
                .fileDeliveryStatus(FileDeliveryStatus.NOT_SENT)
                .build();
        Merchant savedMerchant = merchantRepository.save(merchant);
        log.info("MERCHANT ===> {}",savedMerchant);

        return convertToResponseDto(savedMerchant);
    }

    @Override
    public MerchantResponseDto updateMerchant(Long id, MerchantUpdateRequestDto merchantUpdateDto) {
        Merchant merchant = getMerchantById(id);
        if(merchantUpdateDto.getMerchantID() != null) merchant.setMerchantID(merchantUpdateDto.getMerchantID());
        if(merchantUpdateDto.getAcquirerIdentifier() != null) merchant.setAcquirerIdentifier(merchantUpdateDto.getAcquirerIdentifier());
        merchant.setLastUpdated(LocalDateTime.now());
        if(merchantUpdateDto.getRequestType() != null) merchant.setRequestType(merchantUpdateDto.getRequestType());
        if(merchantUpdateDto.getStatus() != null) merchant.setStatus(merchantUpdateDto.getStatus());
        if(merchantUpdateDto.getFileDeliveryStatus() != null) merchant.setFileDeliveryStatus(merchantUpdateDto.getFileDeliveryStatus());

        log.info("MERCHANT ===> {}",merchant);

        Merchant savedMerchant = merchantRepository.save(merchant);
        return convertToResponseDto(savedMerchant);
    }

    @Override
    public void deleteMerchant(Long id) {
        Merchant merchantOnBoarding = getMerchantById(id);
        merchantRepository.deleteById(merchantOnBoarding.getId());
    }

    @Override
    public boolean validateToken(String token) {
        boolean isValid = false;
        try {
            // Remove "Bearer " prefix from the token
            String jwtToken = token.replace("Bearer ", "");

            // Validate the JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            return isValid;
        } catch (JwtException e) {
            return isValid;
        }
    }
}
