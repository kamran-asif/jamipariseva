package com.jamipariseva.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jamipariseva.dto.service.ServiceItemDto;
import com.jamipariseva.exception.GlobalExceptionHandler;
import com.jamipariseva.service.ServiceCatalogService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServiceCatalogController.class)
@Import(GlobalExceptionHandler.class)
class ServiceCatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceCatalogService serviceCatalogService;

    @Test
    void getServices_returnsServicesList() throws Exception {
        when(serviceCatalogService.getServices(any())).thenReturn(
                List.of(
                        ServiceItemDto.builder()
                                .serviceId("12")
                                .serviceName("Certified copy of Surveyed Khatian")
                                .description("Get a certified copy of surveyed land records")
                                .feeAmount(50.0)
                                .servicePath("/dashboard/khatian-search")
                                .build(),
                        ServiceItemDto.builder()
                                .serviceId("13")
                                .serviceName("Payment of Land Revenue")
                                .description("Pay land taxes and revenue online securely")
                                .feeAmount(0.0)
                                .servicePath("disabled")
                                .build()
                )
        );

        mockMvc.perform(post("/api/getservices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"12345\",\"role_id\":\"6\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].service_id").value("12"))
                .andExpect(jsonPath("$.data[0].service_name").value("Certified copy of Surveyed Khatian"))
                .andExpect(jsonPath("$.data[0].description").value("Get a certified copy of surveyed land records"))
                .andExpect(jsonPath("$.data[0].service_path").value("/dashboard/khatian-search"))
                .andExpect(jsonPath("$.data[1].service_id").value("13"))
                .andExpect(jsonPath("$.data[1].service_name").value("Payment of Land Revenue"))
                .andExpect(jsonPath("$.data[1].description").value("Pay land taxes and revenue online securely"))
                .andExpect(jsonPath("$.data[1].service_path").value("disabled"));
    }

    @Test
    void getServices_missingFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/getservices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"12345\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
