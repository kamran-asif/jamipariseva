package com.jamipariseva.controller;

import com.jamipariseva.dto.service.CitizenServiceRequest;
import com.jamipariseva.service.ServiceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Service Catalog", description = "Endpoints for retrieving available service listings")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceCatalogController {

    private final ServiceCatalogService serviceCatalogService;

    @Operation(summary = "Get available services for a citizen role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request succeeded"),
            @ApiResponse(responseCode = "400", description = "Invalid request/body format", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = """
                    {
                      "success": false,
                      "message": "Malformed JSON or invalid request format",
                      "httpCodes": {
                        "400 Bad Request": "Invalid request/body format",
                        "500 Internal Server Error": "Generic server error"
                      }
                    }
                    """))),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = """
                    {
                      "success": false,
                      "message": "citizen_id: must not be blank",
                      "httpCodes": {
                        "400 Bad Request": "Invalid request/body format",
                        "500 Internal Server Error": "Generic server error"
                      }
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Generic server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.jamipariseva.common.ApiResponse.class), examples = @ExampleObject(value = """
                    {
                      "success": false,
                      "message": "Internal server error. Please try again later."
                    }
                    """)))
    })
    @PostMapping("/getservices")
    public ResponseEntity<?> getServices(@Valid @RequestBody CitizenServiceRequest request) {
        return ResponseEntity.ok(com.jamipariseva.common.ApiResponse.ok(serviceCatalogService.getServices(request)));
    }
}
