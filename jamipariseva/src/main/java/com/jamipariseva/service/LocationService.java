package com.jamipariseva.service;

import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.dto.location.LocationLevel;
import com.jamipariseva.dto.location.LocationRequest;
import com.jamipariseva.exception.BadRequestException;
import com.jamipariseva.mapper.LocationMapper;
import com.jamipariseva.repository.LocationProjection;
import com.jamipariseva.repository.RevenueLocationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final RevenueLocationRepository locationRepository;
    private final LocationMapper locationMapper;

    /**
     * Fetches revenue hierarchy: district → subdivision → circle → tehsil → village/mouja.
     * Each level uses only the immediate parent code from the request body.
     */
    public List<LocationItemDto> fetchLocations(LocationRequest request) {
        LocationLevel level = LocationLevel.from(request.getRequestFor());
        log.debug("Fetching locations for level={}", level);

        List<LocationProjection> rows = switch (level) {
            case DISTRICT -> locationRepository.getDistricts();
            case SUBDIVISION -> {
                requireCode(request.getLgdDistCode(), "lgd_dist_code");
                yield locationRepository.getSubDivisions(request.getLgdDistCode());
            }
            case CIRCLE -> {
                requireCode(request.getLgdSubdivCode(), "lgd_subdiv_code");
                yield locationRepository.getCircles(request.getLgdSubdivCode());
            }
            case TEHSIL -> {
                requireCode(request.getLgdCircleCode(), "lgd_circle_code");
                yield locationRepository.getTehsils(request.getLgdCircleCode());
            }
            case VILLAGE, MOUJA, MOUZA -> {
                requireCode(request.getLgdTehsilCode(), "lgd_tehsil_code");
                yield locationRepository.getVillages(request.getLgdTehsilCode());
            }
        };

        List<LocationItemDto> result = locationMapper.toDtoList(rows);
        if (result.isEmpty()) {
            log.warn("No locations found for level={} request={}", level, request);
        }
        return result;
    }

    private void requireCode(String value, String field) {
        if (!StringUtils.hasText(value)) {
            throw new BadRequestException(field + " is required for this request_for level");
        }
    }
}
