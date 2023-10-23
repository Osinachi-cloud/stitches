package com.stitches.dto.request;

import com.stitches.enums.FileDeliveryStatus;
import com.stitches.enums.RequestType;
import com.stitches.enums.Status;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MerchantUpdateRequestDto{
    private String acquirerIdentifier;
    private String merchantID;
    private RequestType requestType;
    private String cardAcceptorBusinessCode;
    private String email;
    private String mobile;
    private String countryCode;
    private Long icaId;
    private Status status;
    private FileDeliveryStatus fileDeliveryStatus;

}
