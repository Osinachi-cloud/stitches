package com.stitches.repository;

import com.stitches.model.MerchantErrorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantErrorDataRepository extends JpaRepository<MerchantErrorData, Long> {

}
