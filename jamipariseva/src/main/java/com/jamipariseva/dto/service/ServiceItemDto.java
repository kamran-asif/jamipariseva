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

    @JsonProperty("description")
    private String description;

    @JsonProperty("fee_amount")
    private Double feeAmount;

    @JsonProperty("service_path")
    private String servicePath;
}
