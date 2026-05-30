package com.jamipariseva.dto.ror;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RorVerifyResponse {

    private boolean verified;

    @JsonProperty("khatian_no")
    private String khatianNo;

    @JsonProperty("plot_no")
    private String plotNo;

    @JsonProperty("owner_name")
    private String ownerName;

    @JsonProperty("total_share")
    private String totalShare;

    @JsonProperty("mouja_name")
    private String moujaName;

    @JsonProperty("lgd_village_code")
    private String lgdVillageCode;

    @JsonProperty("khatian_detail")
    private KhatianResponseDto khatianDetail;
}
