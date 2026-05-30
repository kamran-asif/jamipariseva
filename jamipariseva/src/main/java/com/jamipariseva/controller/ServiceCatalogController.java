package com.jamipariseva.controller;

import com.jamipariseva.common.ApiResponse;
import com.jamipariseva.dto.service.CitizenServiceRequest;
import com.jamipariseva.service.ServiceCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceCatalogController {

    private final ServiceCatalogService serviceCatalogService;

    @PostMapping("/getservices")
    public ApiResponse<?> getServices(@Valid @RequestBody CitizenServiceRequest request) {
        return ApiResponse.ok(serviceCatalogService.getServices(request));
    }
}
