package com.jamipariseva.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceItemDto {

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_name_bn")
    private String serviceNameBn;

    @JsonProperty("fee_amount")
    private Double feeAmount;
}
