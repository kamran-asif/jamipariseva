package com.jamipariseva.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationRequest {

    @JsonProperty("lgd_dist_code")
    private String lgdDistCode;

    @JsonProperty("lgd_subdiv_code")
    private String lgdSubdivCode;

    @JsonProperty("lgd_circle_code")
    private String lgdCircleCode;

    @JsonProperty("lgd_tehsil_code")
    private String lgdTehsilCode;

    @NotBlank(message = "request_for is required")
    @JsonProperty("request_for")
    private String requestFor;
}
