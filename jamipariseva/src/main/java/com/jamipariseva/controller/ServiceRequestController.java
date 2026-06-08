package com.jamipariseva.controller;

import com.jamipariseva.dto.request.AcknowledgementRequest;
import com.jamipariseva.dto.request.ApplyServiceRequestDto;
import com.jamipariseva.dto.request.DownloadRequest;
import com.jamipariseva.dto.request.RequestStatusRequest;
import com.jamipariseva.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Service Request", description = "Endpoints for applying, querying, and managing service requests")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @Operation(summary = "Apply for a service request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Resource created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request/body format"),
        @ApiResponse(responseCode = "422", description = "Validation failed"),
        @ApiResponse(responseCode = "500", description = "Generic server error")
    })
    @PostMapping("/apply/servicerequest")
    public com.jamipariseva.common.ApiResponse<Map<String, Object>> apply(@Valid @RequestBody ApplyServiceRequestDto request) {
        return com.jamipariseva.common.ApiResponse.ok("Service request saved", serviceRequestService.apply(request));
    }

    @Operation(summary = "Get request status or list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request succeeded"),
        @ApiResponse(responseCode = "400", description = "Invalid request/body format"),
        @ApiResponse(responseCode = "404", description = "Request not found"),
        @ApiResponse(responseCode = "500", description = "Generic server error")
    })
    @PostMapping("/request")
    public com.jamipariseva.common.ApiResponse<?> getRequests(@Valid @RequestBody RequestStatusRequest request) {
        return com.jamipariseva.common.ApiResponse.ok(serviceRequestService.getRequests(request));
    }

    @Operation(summary = "Get request acknowledgement details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request succeeded"),
        @ApiResponse(responseCode = "400", description = "Invalid request/body format"),
        @ApiResponse(responseCode = "404", description = "Request not found"),
        @ApiResponse(responseCode = "500", description = "Generic server error")
    })
    @PostMapping("/acknowledgement")
    public com.jamipariseva.common.ApiResponse<Map<String, Object>> getAcknowledgement(@Valid @RequestBody AcknowledgementRequest request) {
        return com.jamipariseva.common.ApiResponse.ok(serviceRequestService.getAcknowledgement(
                request.getCitizenId(), request.getRoleId(), request.getRequestId()));
    }

    @Operation(summary = "Get PDF download URL for a request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request succeeded"),
        @ApiResponse(responseCode = "400", description = "Invalid request/body format"),
        @ApiResponse(responseCode = "404", description = "Request not found"),
        @ApiResponse(responseCode = "500", description = "Generic server error")
    })
    @PostMapping("/download")
    public com.jamipariseva.common.ApiResponse<Map<String, Object>> getDownloadUrl(@Valid @RequestBody DownloadRequest request) {
        return com.jamipariseva.common.ApiResponse.ok(serviceRequestService.getDownloadUrl(
                request.getServiceId(), request.getCitizenId(), request.getRoleId(), request.getRequestId()));
    }
}
