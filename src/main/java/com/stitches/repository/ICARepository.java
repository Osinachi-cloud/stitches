package com.stitches.repository;

import com.stitches.model.ICA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICARepository extends JpaRepository<ICA, Long> {
//    Optional<List<ICA>> findICAByCountry_Code(String countryCode);
    void deleteICAById(Long id);

}
