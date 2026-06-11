package com.jamipariseva.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jamipariseva.dto.ror.RorVerifyResponse;
import com.jamipariseva.exception.GlobalExceptionHandler;
import com.jamipariseva.service.RorVerifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RorController.class)
@Import(GlobalExceptionHandler.class)
class RorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RorVerifyService rorVerifyService;

    @Test
    void verifyRor_byOwnerName_returnsVerifiedResponse() throws Exception {
        when(rorVerifyService.verify(any())).thenReturn(
                RorVerifyResponse.builder()
                        .verified(true)
                        .khatianNo("11/1")
                        .plotNo("121")
                        .ownerName("Narendra Chandra Pal")
                        .totalShare("1/1")
                        .moujaName("Agartala Sheet No 6")
                        .lgdVillageCode("922855")
                        .build()
        );

        mockMvc.perform(post("/api/ror/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"search_by\":\"owner_name\",\"owner_name\":\"Narendra Chandra Pal\",\"lgd_village_code\":\"922855\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.verified").value(true))
                .andExpect(jsonPath("$.data.khatian_no").value("11/1"))
                .andExpect(jsonPath("$.data.owner_name").value("Narendra Chandra Pal"));
    }

    @Test
    void verifyRor_missingFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/ror/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"search_by\":\"owner_name\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
