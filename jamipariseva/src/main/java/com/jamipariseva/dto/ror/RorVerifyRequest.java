package com.jamipariseva.dto.ror;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body to verify Record of Rights (RoR)")
public class RorVerifyRequest {

    @NotBlank
    @JsonProperty("search_by")
    @Schema(description = "Search criteria type (name or khatian or plot)", example = "name")
    private String searchBy;

    @JsonProperty("owner_name")
    @Schema(description = "Owner's full name in English (used if search_by is name)", example = "Narendra Chandra Pal")
    private String ownerName;

    @JsonProperty("khatian_no")
    @Schema(description = "Khatian Number (used if search_by is khatian)", example = "11/1")
    private String khatianNo;

    @JsonProperty("plot_no")
    @Schema(description = "Plot Number (used if search_by is plot)", example = "121")
    private String plotNo;

    @NotBlank
    @JsonProperty("lgd_village_code")
    @Schema(description = "LGD Village Code", example = "922855")
    private String lgdVillageCode;
}

