package com.jamipariseva.controller;

import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.dto.location.LocationRequest;
import com.jamipariseva.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded"),
            @ApiResponse(responseCode = "400", description = "Invalid request/body format", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"lgd_dist_code is required for this request_for level\",\n  \"httpCodes\": {\n    \"400 Bad Request\": \"Invalid request/body format\",\n    \"500 Internal Server Error\": \"Generic server error\"\n  }\n}"))),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"request_for: must not be blank\",\n  \"httpCodes\": {\n    \"400 Bad Request\": \"Invalid request/body format\",\n    \"500 Internal Server Error\": \"Generic server error\"\n  }\n}"))),
            @ApiResponse(responseCode = "500", description = "Generic server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Internal server error. Please try again later.\"\n}")))
    })
    @PostMapping("/location")
    public com.jamipariseva.common.ApiResponse<List<LocationItemDto>> getLocations(
            @Valid @RequestBody LocationRequest request) {
        return com.jamipariseva.common.ApiResponse.ok(locationService.fetchLocations(request));
    }
}
