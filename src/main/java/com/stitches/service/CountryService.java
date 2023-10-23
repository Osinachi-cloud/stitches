package com.stitches.service;


import com.stitches.dto.request.CountryDTO;
import com.stitches.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    CountryDTO addCountry(CountryDTO countryDTO);
    Optional<List<Country>> findAllCountries();

    Optional<Country> findCountryByCode(String code);

    void deleteCountryById(Long id);
}
