package com.jamipariseva.mapper;

import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.repository.LocationProjection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationItemDto toDto(LocationProjection projection) {
        return LocationItemDto.builder()
                .code(projection.getCode())
                .nameEng(projection.getNameEng())
                .build();
    }

    public List<LocationItemDto> toDtoList(List<LocationProjection> rows) {
        return rows.stream().map(this::toDto).toList();
    }
}
