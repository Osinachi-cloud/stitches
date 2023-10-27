package com.stitches.service.serviceImpl;

import com.stitches.dto.request.BodyMeasurementRequest;
import com.stitches.dto.response.BodyMeasurementResponse;
import com.stitches.exceptions.ApiRequestException;
import com.stitches.mapper.DtoMapper;
import com.stitches.model.AppUser;
import com.stitches.model.BodyMeasurement;
import com.stitches.repository.BodyMeasurementRepository;
import com.stitches.repository.UserRepository;
import com.stitches.service.BodyMeasurementService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BodyMeasurementServiceImpl implements BodyMeasurementService {

    private final BodyMeasurementRepository bodyMeasurementRepository;
    private final UserRepository userRepository;

    public BodyMeasurementServiceImpl(BodyMeasurementRepository bodyMeasurementRepository, UserRepository userRepository) {
        this.bodyMeasurementRepository = bodyMeasurementRepository;
        this.userRepository = userRepository;
    }




    @Override
    public BodyMeasurementResponse saveBodyMeasurement(BodyMeasurementRequest request, String customerEmail) {
         Optional<AppUser> optionalAppUser =  userRepository.findAppUserByEmail(customerEmail);
         if(optionalAppUser.isEmpty()){
             throw new ApiRequestException("user with email: " + customerEmail + " does not exist");
         }
         else if(optionalAppUser.get().getBodyMeasurement() != null){
            throw new ApiRequestException("measurement details already exists you can go and edit the existing one");
        }
         else {

                 BodyMeasurement bodyMeasurement = DtoMapper.convertBodyMeasurementDtoToBodyMeasurement(request);
                 BodyMeasurement savedBodyMeasurement = bodyMeasurementRepository.save(bodyMeasurement);

                 optionalAppUser.get().setBodyMeasurement(savedBodyMeasurement);
                 AppUser userWithMeasurement = optionalAppUser.get();
                 AppUser appUser = userRepository.save(userWithMeasurement);
                 BodyMeasurement bodyMeasurementOfUser = appUser.getBodyMeasurement();

                 return DtoMapper.convertBodyMeasurementToBodyMeasurementDto(bodyMeasurementOfUser);


         }
    }
}
