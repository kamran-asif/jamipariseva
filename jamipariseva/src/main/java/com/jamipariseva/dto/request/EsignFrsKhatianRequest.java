package com.jamipariseva.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EsignFrsKhatianRequest {

    @NotBlank(message = "Khatian number is required")
    @JsonProperty("khatian_no")
    @JsonAlias("Khatian_no")
    private String khatianNo;

    @NotBlank(message = "LGD village code is required")
    @JsonProperty("lgd_village_code")
    private String lgdVillageCode;

    @NotBlank(message = "LGD district code is required")
    @JsonProperty("lgd_district_code")
    private String lgdDistrictCode;
}
