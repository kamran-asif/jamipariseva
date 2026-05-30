package com.jamipariseva.controller;

import com.jamipariseva.common.ApiResponse;
import com.jamipariseva.dto.ror.RorVerifyRequest;
import com.jamipariseva.service.RorVerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RorController {

    private final RorVerifyService rorVerifyService;

    @PostMapping("/ror/verify")
    public ApiResponse<?> verifyRor(@Valid @RequestBody RorVerifyRequest request) {
        return ApiResponse.ok(rorVerifyService.verify(request));
    }
}
