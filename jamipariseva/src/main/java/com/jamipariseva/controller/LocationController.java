package com.jamipariseva.controller;

import com.jamipariseva.common.ApiResponse;
import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.dto.location.LocationRequest;
import com.jamipariseva.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Location", description = "Revenue hierarchy: district → subdivision → circle → tehsil → village/mouja")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "Fetch locations", description = """
            request_for values: district, subdivision, circle, tehsil, village, mouja.
            Parent code required per level: lgd_dist_code, lgd_subdiv_code, lgd_circle_code, lgd_tehsil_code.
            """)
    @PostMapping("/location")
    public ApiResponse<List<LocationItemDto>> getLocations(@Valid @RequestBody LocationRequest request) {
        return ApiResponse.ok(locationService.fetchLocations(request));
    }
}
