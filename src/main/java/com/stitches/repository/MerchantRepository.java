package com.stitches.repository;

import com.stitches.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
//    Page<Merchant> findByAcquirerIdentifierContaining(String merchantId, Pageable pageable);
//    @Query("select m from Merchant m where upper(m.status) like upper(?1)")
    @Query(value="SELECT * from Merchant m where m.acquirer_identifier = (?)", nativeQuery = true)
    List<Merchant> findMerchantsByAcquirerIdentifier(String ica);

    Optional<Merchant> findMerchantByMerchantID(String merchantId);


//    List<Merchant> findAll();

    @Query(value = "SELECT * from Merchant m where upper(m.status) like upper(?1) ",
            nativeQuery = true)
    Page<Merchant> findMerchantsByStatusContaining(String status, Pageable pageable);

    @Query(value = "SELECT * FROM Merchant WHERE " +
            "(status = ?1 OR ?1 IS NULL) AND " +
            "(acquirer_identifier = ?2 OR ?2 IS NULL)",
            nativeQuery = true)
    Page<Merchant> findAllBy(
            String status,
            String acquirerIdentifier,
            String merchantID,
            String requestType,
            String cardAcceptorBusinessCode,
            String requestDate,
            Pageable pageable
    );


}
