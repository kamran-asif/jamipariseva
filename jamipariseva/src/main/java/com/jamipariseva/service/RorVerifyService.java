package com.jamipariseva.service;

import com.jamipariseva.dto.ror.KhatianResponseDto;
import com.jamipariseva.dto.ror.RorVerifyRequest;
import com.jamipariseva.dto.ror.RorVerifyResponse;
import com.jamipariseva.entity.RorRecordEntity;
import com.jamipariseva.exception.BadRequestException;
import com.jamipariseva.exception.ResourceNotFoundException;
import com.jamipariseva.mapper.KhatianMapper;
import com.jamipariseva.repository.KhatianRepository;
import com.jamipariseva.repository.RorRecordRepository;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDateTime;
import com.jamipariseva.dto.request.EsignFrsKhatianRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RorVerifyService {

    private final RorRecordRepository rorRecordRepository;
    private final KhatianRepository khatianRepository;
    private final KhatianMapper khatianMapper;

    public RorVerifyResponse verify(RorVerifyRequest request) {
        String searchBy = request.getSearchBy().trim().toLowerCase();
        Optional<RorRecordEntity> record = switch (searchBy) {
            case "khatian" -> {
                if (!StringUtils.hasText(request.getKhatianNo())) {
                    throw new BadRequestException("khatian_no is required when search_by is khatian");
                }
                yield rorRecordRepository.findByLgdVillageCodeAndKhatianNo(
                        request.getLgdVillageCode(), request.getKhatianNo());
            }
            case "plot" -> {
                if (!StringUtils.hasText(request.getPlotNo())) {
                    throw new BadRequestException("plot_no is required when search_by is plot");
                }
                yield rorRecordRepository.findByLgdVillageCodeAndPlotNo(
                        request.getLgdVillageCode(), request.getPlotNo());
            }
            case "owner_name" -> {
                if (!StringUtils.hasText(request.getSfname()) || !StringUtils.hasText(request.getSlname())) {
                    throw new BadRequestException("sfname and slname are required when search_by is owner_name");
                }
                yield rorRecordRepository.findByLgdVillageCodeAndSfnameAndSlname(
                        request.getLgdVillageCode(), request.getSfname(), request.getSlname());
            }
            default -> throw new BadRequestException("Invalid search_by. Use: owner_name, khatian, plot");
        };

        RorRecordEntity ror = record.orElseThrow(() -> new ResourceNotFoundException("RoR record not found"));
        KhatianResponseDto khatianDetail = resolveKhatianDetail(ror);

        return RorVerifyResponse.builder()
                .verified(true)
                .khatianNo(ror.getKhatianNo())
                .plotNo(ror.getPlotNo())
                .ownerName(ror.getOwnerName())
                .totalShare(ror.getTotalShare())
                .moujaName(ror.getMoujaName())
                .lgdVillageCode(ror.getLgdVillageCode())
                .khatianDetail(khatianDetail)
                .build();
    }

    private KhatianResponseDto resolveKhatianDetail(RorRecordEntity ror) {
        return khatianRepository
                .findByLgdVillageCodeAndKhatianNo(ror.getLgdVillageCode(), ror.getKhatianNo())
                .map(khatianMapper::toDto)
                .orElseGet(() -> khatianMapper.fromRorRecord(ror));
    }

    public Map<String, Object> esignFrsKhatian(EsignFrsKhatianRequest request) {
        // Verify that the khatian record exists in our database
        khatianRepository.findByLgdVillageCodeAndKhatianNo(request.getLgdVillageCode(), request.getKhatianNo())
                .orElseThrow(() -> new ResourceNotFoundException("Khatian record not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("khatian_no", request.getKhatianNo());
        response.put("lgd_village_code", request.getLgdVillageCode());
        response.put("lgd_district_code", request.getLgdDistrictCode());
        response.put("status", "SUCCESS");
        response.put("message", "First revision surveyed khatian signed successfully");
        response.put("esign_transaction_id", "TXN-ESG-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase());
        response.put("signed_at", LocalDateTime.now().toString());
        return response;
    }
}
