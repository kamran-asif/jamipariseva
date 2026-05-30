package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestStatusRequest {

    @NotBlank
    @JsonProperty("citizen_id")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    private String roleId;

    @NotBlank
    @JsonProperty("request_for")
    private String requestFor;

    @JsonProperty("request_id")
    private String requestId;
}
