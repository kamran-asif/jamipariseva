package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body to fetch request acknowledgement")
public class AcknowledgementRequest {

    @NotBlank
    @JsonProperty("citizen_id")
    @Schema(description = "Citizen ID", example = "2823")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    @Schema(description = "Role ID", example = "6")
    private String roleId;

    @NotNull
    @JsonProperty("request_id")
    @Schema(description = "Request ID", example = "850946")
    private Long requestId;
}

