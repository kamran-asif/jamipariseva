package com.jamipariseva.mapper;

import com.jamipariseva.dto.ror.KhatianResponseDto;
import com.jamipariseva.entity.KhatianEntity;
import com.jamipariseva.entity.RorRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class KhatianMapper {

    public KhatianResponseDto toDto(KhatianEntity entity) {
        if (entity == null) {
            return null;
        }
        return KhatianResponseDto.builder()
                .idn(entity.getIdn())
                .ktsr(entity.getKtsr())
                .khatianNo(entity.getKhatianNo())
                .lgdVillageCode(entity.getLgdVillageCode())
                .blobData(entity.getBlobData())
                .fileFormate(entity.getFileFormate())
                .hasDocument(entity.getBlobData() != null && entity.getBlobData().length > 0)
                .entryDate(entity.getEntryDate())
                .enteredBy(entity.getEnteredBy())
                .entryIp(entity.getEntryIp())
                .verifiedBy(entity.getVerifiedBy())
                .verificationDate(entity.getVerificationDate())
                .verificationIp(entity.getVerificationIp())
                .approvedBy(entity.getApprovedBy())
                .approvedDate(entity.getApprovedDate())
                .approvedIp(entity.getApprovedIp())
                .modificationDate(entity.getModificationDate())
                .modifiedBy(entity.getModifiedBy())
                .modificationIp(entity.getModificationIp())
                .kt1(entity.getKt1())
                .kt2(entity.getKt2())
                .isCancelled(entity.getIsCancelled())
                .build();
    }

    /** Minimal khatian info when only RoR row exists (no khatian_record row). */
    public KhatianResponseDto fromRorRecord(RorRecordEntity ror) {
        return KhatianResponseDto.builder()
                .khatianNo(ror.getKhatianNo())
                .lgdVillageCode(ror.getLgdVillageCode())
                .ktsr(ror.getKhatianNo())
                .hasDocument(false)
                .isCancelled("N")
                .build();
    }
}
