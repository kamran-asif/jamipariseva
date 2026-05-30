package com.jamipariseva.controller;

import com.jamipariseva.common.ApiResponse;
import com.jamipariseva.dto.request.AcknowledgementRequest;
import com.jamipariseva.dto.request.ApplyServiceRequestDto;
import com.jamipariseva.dto.request.DownloadRequest;
import com.jamipariseva.dto.request.RequestStatusRequest;
import com.jamipariseva.service.ServiceRequestService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping("/apply/servicerequest")
    public ApiResponse<Map<String, Object>> apply(@Valid @RequestBody ApplyServiceRequestDto request) {
        return ApiResponse.ok("Service request saved", serviceRequestService.apply(request));
    }

    @PostMapping("/request")
    public ApiResponse<?> getRequests(@Valid @RequestBody RequestStatusRequest request) {
        return ApiResponse.ok(serviceRequestService.getRequests(request));
    }

    @PostMapping("/acknowledgement")
    public ApiResponse<Map<String, Object>> acknowledgement(@Valid @RequestBody AcknowledgementRequest request) {
        return ApiResponse.ok(serviceRequestService.getAcknowledgement(
                request.getCitizenId(), request.getRoleId(), request.getRequestId()));
    }

    @PostMapping("/download")
    public ApiResponse<Map<String, Object>> download(@Valid @RequestBody DownloadRequest request) {
        return ApiResponse.ok(serviceRequestService.getDownloadUrl(
                request.getServiceId(),
                request.getCitizenId(),
                request.getRoleId(),
                request.getRequestId()));
    }
}
