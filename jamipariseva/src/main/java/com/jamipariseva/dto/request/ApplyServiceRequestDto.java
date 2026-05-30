package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplyServiceRequestDto {

    @NotBlank
    @JsonProperty("citizen_id")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    private String roleId;

    @NotBlank
    @JsonProperty("service_id")
    private String serviceId;

    @NotNull
    @JsonProperty("payment_multiply_factor")
    private JsonNode paymentMultiplyFactor;

    @NotNull
    @JsonProperty("rorinfo")
    private JsonNode rorinfo;

    @NotNull
    @JsonProperty("applicantinfo")
    private JsonNode applicantinfo;
}
