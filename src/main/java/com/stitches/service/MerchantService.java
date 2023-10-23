package com.stitches.service;


import com.stitches.dto.request.MerchantRequestDto;
import com.stitches.dto.request.MerchantUpdateRequestDto;
import com.stitches.dto.response.MerchantResponseDto;
import com.stitches.model.Merchant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MerchantService {

    MerchantResponseDto findMerchantById (long id);
    Page<Merchant> getMerchants(
            String status,
            String acquirerIdentifier,
            String merchantID,
            String requestType,
            String cardAcceptorBusinessCode,
            String requestDate,
            int page,
            int size
    );
    Page<Merchant> getMerchantsByStatus(String status, int page, int size);
    MerchantResponseDto addMerchant (MerchantRequestDto merchantOnBoarding);
    MerchantResponseDto updateMerchant (Long id, MerchantUpdateRequestDto merchantOnBoarding);

    void deleteMerchant(Long id);

    List<Merchant> getAllMerchantsByICa(String ica);

    List<Merchant> getAllMerchants();

    boolean validateToken(String token);


//    Page<Merchant> findByStatus(Status , Pageable );
}
