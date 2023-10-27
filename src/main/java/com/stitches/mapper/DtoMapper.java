package com.stitches.mapper;

import com.stitches.dto.request.BodyMeasurementRequest;
import com.stitches.dto.response.BodyMeasurementResponse;
import com.stitches.model.BodyMeasurement;

public class DtoMapper {

    public static BodyMeasurement convertBodyMeasurementDtoToBodyMeasurement(BodyMeasurementRequest bodyMeasurementRequest){

        BodyMeasurement bodyMeasurement = new BodyMeasurement();
        bodyMeasurement.setAnkle(bodyMeasurementRequest.getAnkle());
        bodyMeasurement.setKnee(bodyMeasurementRequest.getKnee());
        bodyMeasurement.setChest(bodyMeasurementRequest.getChest());
        bodyMeasurement.setNeck(bodyMeasurementRequest.getNeck());
        bodyMeasurement.setHipWidth(bodyMeasurementRequest.getHipWidth());
        bodyMeasurement.setThigh(bodyMeasurementRequest.getThigh());
        bodyMeasurement.setLongSleeveAtWrist(bodyMeasurementRequest.getLongSleeveAtWrist());
        bodyMeasurement.setMidSleeveAtElbow(bodyMeasurementRequest.getMidSleeveAtElbow());
        bodyMeasurement.setShortSleeveAtBiceps(bodyMeasurementRequest.getShortSleeveAtBiceps());
        bodyMeasurement.setNeckToHipLength(bodyMeasurementRequest.getNeckToHipLength());
        bodyMeasurement.setShoulder(bodyMeasurementRequest.getShoulder());
        bodyMeasurement.setTrouserLength(bodyMeasurementRequest.getTrouserLength());
        bodyMeasurement.setTummy(bodyMeasurementRequest.getTummy());
        bodyMeasurement.setWaist(bodyMeasurementRequest.getWaist());

        return bodyMeasurement;
    }

    public static BodyMeasurementResponse convertBodyMeasurementToBodyMeasurementDto(BodyMeasurement bodyMeasurement){

        BodyMeasurementResponse bodyMeasurementResponse = new BodyMeasurementResponse();
        bodyMeasurementResponse.setAnkle(bodyMeasurement.getAnkle());
        bodyMeasurementResponse.setKnee(bodyMeasurement.getKnee());
        bodyMeasurementResponse.setChest(bodyMeasurement.getChest());
        bodyMeasurementResponse.setNeck(bodyMeasurement.getNeck());
        bodyMeasurementResponse.setHipWidth(bodyMeasurement.getHipWidth());
        bodyMeasurementResponse.setThigh(bodyMeasurement.getThigh());
        bodyMeasurementResponse.setLongSleeveAtWrist(bodyMeasurement.getLongSleeveAtWrist());
        bodyMeasurementResponse.setMidSleeveAtElbow(bodyMeasurement.getMidSleeveAtElbow());
        bodyMeasurementResponse.setShortSleeveAtBiceps(bodyMeasurement.getShortSleeveAtBiceps());
        bodyMeasurementResponse.setNeckToHipLength(bodyMeasurement.getNeckToHipLength());
        bodyMeasurementResponse.setShoulder(bodyMeasurement.getShoulder());
        bodyMeasurementResponse.setTrouserLength(bodyMeasurement.getTrouserLength());
        bodyMeasurementResponse.setTummy(bodyMeasurement.getTummy());
        bodyMeasurementResponse.setWaist(bodyMeasurement.getWaist());

        return bodyMeasurementResponse;
    }
}
