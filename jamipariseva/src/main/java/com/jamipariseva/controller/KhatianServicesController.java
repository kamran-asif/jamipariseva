package com.jamipariseva.controller;

import com.jamipariseva.common.ApiResponse;
import com.jamipariseva.dto.ror.RorVerifyRequest;
import com.jamipariseva.dto.request.EsignFrsKhatianRequest;
import com.jamipariseva.service.RorVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/khatian_services")
@RequiredArgsConstructor
public class KhatianServicesController {

    private final RorVerifyService rorVerifyService;

    @PostMapping("/verify/khatian")
    public ApiResponse<?> verifyKhatian(@Valid @RequestBody RorVerifyRequest request) {
        request.setSearchBy("khatian");
        return ApiResponse.ok(rorVerifyService.verify(request));
    }

    @PostMapping("/verify/plot")
    public ApiResponse<?> verifyPlot(@Valid @RequestBody RorVerifyRequest request) {
        request.setSearchBy("plot");
        return ApiResponse.ok(rorVerifyService.verify(request));
    }

    @PostMapping("/esign/frskhatian")
    public ApiResponse<?> esignFrsKhatian(@Valid @RequestBody EsignFrsKhatianRequest request) {
        return ApiResponse.ok(rorVerifyService.esignFrsKhatian(request));
    }
}
