package com.stitches.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.stitches.enums.FileDeliveryStatus;
import com.stitches.enums.RequestType;
import com.stitches.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantResponseDto {

    private String acquirerIdentifier;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdated;

    private String merchantID;
    private RequestType requestType;
    private String cardAcceptorBusinessCode;
    private Status status;
    private String email;
    private String mobile;
    private String countryName;
    private String icaName;
    private FileDeliveryStatus fileDeliveryStatus;

}
