package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body to apply for a service request")
public class ApplyServiceRequestDto {

    @NotBlank
    @JsonProperty("citizen_id")
    @Schema(description = "Citizen ID", example = "2823")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    @Schema(description = "Role ID", example = "6")
    private String roleId;

    @NotBlank
    @JsonProperty("service_id")
    @Schema(description = "Service ID (e.g., 10, 11, 12, 13, 14)", example = "12")
    private String serviceId;

    @NotNull
    @JsonProperty("payment_multipy_factor")
    @Schema(description = "Payment multiplication factor list", example = "[{\"count\": 1}]")
    private JsonNode paymentMultipyFactor;

    @NotNull
    @JsonProperty("rorinfo")
    @Schema(description = "RoR details list", example = "[{\"search_value\": \"11/1\", \"khatian_no\": \"11/1\", \"mouja_val\": \"922855\", \"search_type\": \"1\", \"is_khatian_required\": \"1\"}]")
    private JsonNode rorinfo;

    @NotNull
    @JsonProperty("applicantinfo")
    @Schema(description = "Applicant information dictionary", example = "{\"name\": \"Narendra Chandra Pal\"}")
    private JsonNode applicantinfo;
}

