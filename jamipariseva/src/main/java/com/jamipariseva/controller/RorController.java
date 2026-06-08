package com.jamipariseva.controller;

import com.jamipariseva.dto.ror.RorVerifyRequest;
import com.jamipariseva.service.RorVerifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Record of Rights (RoR)", description = "Endpoints for RoR/Khatian/Plot verification")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RorController {

    private final RorVerifyService rorVerifyService;

    @Operation(summary = "Verify Record of Rights (RoR)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request succeeded"),
        @ApiResponse(responseCode = "400", description = "Invalid request/body format"),
        @ApiResponse(responseCode = "422", description = "Validation failed"),
        @ApiResponse(responseCode = "500", description = "Generic server error")
    })
    @PostMapping("/ror/verify")
    public com.jamipariseva.common.ApiResponse<?> verifyRor(@Valid @RequestBody RorVerifyRequest request) {
        return com.jamipariseva.common.ApiResponse.ok(rorVerifyService.verify(request));
    }
}
