package com.jamipariseva.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CitizenServiceRequest {

    @NotBlank
    @JsonProperty("citizen_id")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    private String roleId;
}
