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
                if (!StringUtils.hasText(request.getOwnerName())) {
                    throw new BadRequestException("owner_name is required when search_by is owner_name");
                }
                yield rorRecordRepository.findByLgdVillageCodeAndOwnerNameIgnoreCase(
                        request.getLgdVillageCode(), request.getOwnerName());
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
}
