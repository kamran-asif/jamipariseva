package com.jamipariseva.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jamipariseva.dto.location.LocationItemDto;
import com.jamipariseva.exception.GlobalExceptionHandler;
import com.jamipariseva.service.LocationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LocationController.class)
@Import(GlobalExceptionHandler.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationService locationService;

    @Test
    void postLocation_returnsWrappedResponse() throws Exception {
        when(locationService.fetchLocations(any())).thenReturn(
                List.of(LocationItemDto.builder().code("272").nameEng("West Tripura").build()));

        mockMvc.perform(post("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lgd_dist_code\":\"\",\"request_for\":\"district\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].code").value("272"))
                .andExpect(jsonPath("$.data[0].name_eng").value("West Tripura"));
    }

    @Test
    void postLocation_missingRequestFor_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lgd_dist_code\":\"272\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
