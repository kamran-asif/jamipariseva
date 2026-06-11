package com.jamipariseva.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body to fetch location hierarchy list")
public class LocationRequest {

    @JsonProperty("lgd_dist_code")
    @Schema(description = "LGD District Code (required if request_for is subdivision)", example = "272")
    private String lgdDistCode;

    @JsonProperty("lgd_subdiv_code")
    @Schema(description = "LGD Subdivision Code (required if request_for is circle)", example = "6696")
    private String lgdSubdivCode;

    @JsonProperty("lgd_circle_code")
    @Schema(description = "LGD Circle Code (required if request_for is tehsil)", example = "56")
    private String lgdCircleCode;

    @JsonProperty("lgd_tehsil_code")
    @Schema(description = "LGD Tehsil Code (required if request_for is village or mouja)", example = "8817")
    private String lgdTehsilCode;

    @NotBlank(message = "request_for is required")
    @JsonProperty("request_for")
    @Schema(description = "Level to fetch (district, subdivision, circle, tehsil, village, mouja)", example = "district")
    private String requestFor;
}

