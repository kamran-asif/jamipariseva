package com.jamipariseva.dto.ror;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RorVerifyRequest {

    @NotBlank
    @JsonProperty("search_by")
    private String searchBy;

    @JsonProperty("sfname")
    private String sfname;

    @JsonProperty("slname")
    private String slname;

    @JsonProperty("khatian_no")
    private String khatianNo;

    @JsonProperty("plot_no")
    private String plotNo;

    @NotBlank
    @JsonProperty("lgd_village_code")
    private String lgdVillageCode;
}
