package com.stitches.service;


import com.stitches.dto.request.ICADTO;

import java.util.List;

public interface ICAService {
    List<ICADTO> addICA(List<ICADTO> icaDTO, String countryCode);
//    Optional<List<ICA>> findICAByCountry_Code(String countryCode);
    void deleteICAById(Long id);
}
