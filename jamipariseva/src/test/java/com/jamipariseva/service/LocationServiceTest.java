package com.jamipariseva.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.dto.location.LocationRequest;
import com.jamipariseva.exception.BadRequestException;
import com.jamipariseva.repository.LocationProjection;
import com.jamipariseva.repository.RevenueLocationRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jamipariseva.mapper.LocationMapper;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private RevenueLocationRepository locationRepository;

    @Spy
    private LocationMapper locationMapper = new LocationMapper();

    @InjectMocks
    private LocationService locationService;

    @Test
    void fetchDistricts_returnsMappedList() {
        when(locationRepository.getDistricts()).thenReturn(List.of(projection("272", "West Tripura")));

        LocationRequest request = new LocationRequest();
        request.setRequestFor("district");

        List<LocationItemDto> result = locationService.fetchLocations(request);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("272");
        assertThat(result.get(0).getNameEng()).isEqualTo("West Tripura");
    }

    @Test
    void fetchSubdivision_requiresDistCode() {
        LocationRequest request = new LocationRequest();
        request.setRequestFor("subdivision");

        assertThatThrownBy(() -> locationService.fetchLocations(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("lgd_dist_code");
    }

    @Test
    void fetchCircle_usesSubdivCode() {
        when(locationRepository.getCircles("6696")).thenReturn(List.of(projection("56", "Agartala")));

        LocationRequest request = new LocationRequest();
        request.setRequestFor("circle");
        request.setLgdSubdivCode("6696");

        List<LocationItemDto> result = locationService.fetchLocations(request);

        assertThat(result.get(0).getCode()).isEqualTo("56");
        verify(locationRepository).getCircles("6696");
    }

    @Test
    void fetchVillage_acceptsMoujaAlias() {
        when(locationRepository.getVillages("8817")).thenReturn(List.of(projection("922855", "Agartala Sheet No 6")));

        LocationRequest request = new LocationRequest();
        request.setRequestFor("mouja");
        request.setLgdTehsilCode("8817");

        List<LocationItemDto> result = locationService.fetchLocations(request);

        assertThat(result).hasSize(1);
        verify(locationRepository).getVillages("8817");
    }

    @Test
    void invalidRequestFor_throwsBadRequest() {
        LocationRequest request = new LocationRequest();
        request.setRequestFor("invalid");

        assertThatThrownBy(() -> locationService.fetchLocations(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid request_for");
    }

    private LocationProjection projection(String code, String nameEng) {
        return new LocationProjection() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getNameEng() {
                return nameEng;
            }
        };
    }
}
