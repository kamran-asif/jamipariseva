package com.jamipariseva.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body to fetch services available for a citizen")
public class CitizenServiceRequest {

    @NotBlank
    @JsonProperty("citizen_id")
    @Schema(description = "Citizen ID", example = "2823")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    @Schema(description = "Role ID", example = "6")
    private String roleId;
}

