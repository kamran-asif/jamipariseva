package com.jamipariseva.dto.ror;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhatianResponseDto {

    @JsonProperty("idn")
    private String idn;

    @JsonProperty("ktsr")
    private String ktsr;

    @JsonProperty("khatian_no")
    private String khatianNo;

    @JsonProperty("lgd_village_code")
    private String lgdVillageCode;

    /** Binary document — omitted from JSON; use a separate download API in production. */
    @JsonIgnore
    private byte[] blobData;

    @JsonProperty("file_formate")
    private String fileFormate;

    @JsonProperty("has_document")
    private boolean hasDocument;

    @JsonProperty("entry_date")
    private LocalDateTime entryDate;

    @JsonProperty("entered_by")
    private Integer enteredBy;

    @JsonProperty("entry_ip")
    private String entryIp;

    @JsonProperty("verified_by")
    private Integer verifiedBy;

    @JsonProperty("verification_date")
    private LocalDateTime verificationDate;

    @JsonProperty("verification_ip")
    private String verificationIp;

    @JsonProperty("approved_by")
    private Integer approvedBy;

    @JsonProperty("approved_date")
    private LocalDateTime approvedDate;

    @JsonProperty("approved_ip")
    private String approvedIp;

    @JsonProperty("modification_date")
    private LocalDateTime modificationDate;

    @JsonProperty("modified_by")
    private Integer modifiedBy;

    @JsonProperty("modification_ip")
    private String modificationIp;

    @JsonProperty("kt1")
    private Integer kt1;

    @JsonProperty("kt2")
    private Integer kt2;

    @JsonProperty("is_cancelled")
    private String isCancelled;
}
