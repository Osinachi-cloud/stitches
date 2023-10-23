package com.stitches.service.serviceImpl;

import com.stitches.dto.request.ICADTO;
import com.stitches.exceptions.ApiRequestException;
import com.stitches.model.Country;
import com.stitches.model.ICA;
import com.stitches.repository.CountryRepository;
import com.stitches.repository.ICARepository;
import com.stitches.service.ICAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ICAServiceImpl implements ICAService {

    private Logger log = LoggerFactory.getLogger(ICAServiceImpl.class);
    private final ICARepository icaRepository;
    private final CountryRepository countryRepository;

    public ICAServiceImpl(ICARepository icaRepository, CountryRepository countryRepository){
        this.icaRepository =  icaRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public List<ICADTO> addICA(List<ICADTO> icaDTOList, String countryCode) {
        Optional<Country> country = countryRepository.findCountryByCode(countryCode);
        if(country.isEmpty()){
            throw new ApiRequestException("Country with " + countryCode + " does not exist");
        }
        // copy the icaDto object from its list to the ica list
        List<ICA> icaList = new ArrayList<>();
        icaDTOList.forEach((ICADTO icaDto)->{
            ICA ica = new ICA();
            ica.setCode(icaDto.getCode());
            ica.setName(icaDto.getName());
//            ica.setCountry(icaDto.getCountry());
            icaList.add(ica);
        });
        log.info("This is the service ICADTO list {}", icaDTOList);
        log.info("This is the service ICA list {}", icaList);

        List<ICA> savedICAList = new ArrayList<>();
        List<ICADTO> icaResponseList =  new ArrayList<>();

        icaList.forEach((ICA ica)-> {
            ICA savedICA =  icaRepository.save(ica);
            log.info("This is the service ICA each {}", savedICA);
            savedICAList.add(savedICA);
            ICADTO icadto = new ICADTO();
            icadto.setCode(savedICA.getCode());
            icadto.setName(savedICA.getName());
            icaResponseList.add(icadto);

        });
            Country country1 = country.get();
            country1.setIca(icaList);
            countryRepository.save(country1);
        return icaResponseList;
    }

//    @Override
//    public Optional<List<ICA>> findICAByCountry_Code(String countryCode) {
//        Optional<List<ICA>>  icas = icaRepository.findICAByCountry_Code(countryCode);
//        if(!icas.isPresent()){
//            throw new ICANotFoundException("ICA not found");
//        }
//        return icas;
//
//    }

    @Override
    public void deleteICAById(Long id) {
        icaRepository.deleteICAById(id);
    }
}
