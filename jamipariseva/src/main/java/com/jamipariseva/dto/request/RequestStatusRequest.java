package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body to fetch request status list or single detail")
public class RequestStatusRequest {

    @NotBlank
    @JsonProperty("citizen_id")
    @Schema(description = "Citizen ID", example = "2823")
    private String citizenId;

    @NotBlank
    @JsonProperty("role_id")
    @Schema(description = "Role ID", example = "6")
    private String roleId;

    @NotBlank
    @JsonProperty("request_for")
    @Schema(description = "Request status filter (pending, success, or list)", example = "success")
    private String requestFor;

    @JsonProperty("request_id")
    @JsonAlias("pending_id")
    @Schema(description = "Optional Request ID", example = "850946")
    private Long requestId;
}

