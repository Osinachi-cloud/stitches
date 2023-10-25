package com.stitches.service.serviceImpl;

import com.stitches.dto.request.CountryDTO;
import com.stitches.exceptions.ApiRequestException;
//import com.stitches.model.Country;
import com.stitches.model.Country;
import com.stitches.repository.CountryRepository;
import com.stitches.service.CountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDTO addCountry(CountryDTO countryDTO) {

        Optional<Country> optionalCountry = countryRepository.findCountryByCodeOrName(countryDTO.getCode(), countryDTO.getName());

        if(optionalCountry.isPresent()){
            throw new ApiRequestException("Country name: " + countryDTO.getName() + " " + "country code: " + countryDTO.getCode() + " already exists");
        }else{
            Country country = new Country();
            BeanUtils.copyProperties(countryDTO, country);
            Country savedCountry = countryRepository.save(country);
            CountryDTO countryDTOResponse = new CountryDTO();
            BeanUtils.copyProperties(savedCountry, countryDTOResponse);
            return countryDTOResponse;
        }
    }

    @Override
    public Optional<List<Country>> findAllCountries() {
        Optional<List<Country>> countries = Optional.of(countryRepository.findAll());
        if(!countries.isPresent()){
            throw new ApiRequestException("Countries not found");
        }
        return countries;
    }

    @Override
    public Optional<Country> findCountryByCode(String code) {
        Optional<Country> country = countryRepository.findCountryByCode(code);
        if(!country.isPresent()){
            throw new ApiRequestException("Country not found");
        }
        return country;
    }

    @Override
    public void deleteCountryById(Long id) {
        countryRepository.deleteCountryById(id);
    }
}
