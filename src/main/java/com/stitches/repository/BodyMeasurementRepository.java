package com.stitches.repository;

import com.stitches.dto.request.BodyMeasurementRequest;
import com.stitches.model.BodyMeasurement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {

//    BodyMeasurement save(BodyMeasurementRequest request, Long customerId);



}
