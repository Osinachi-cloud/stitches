package com.stitches.repository;

import com.stitches.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findCountryByCode(String code);

    Optional<Country> findCountryByCodeOrName(String code, String name);
    void deleteCountryById(Long id);
}
