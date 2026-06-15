package com.jamipariseva.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jamipariseva.exception.GlobalExceptionHandler;
import com.jamipariseva.service.ServiceRequestService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServiceRequestController.class)
@Import(GlobalExceptionHandler.class)
class ServiceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceRequestService serviceRequestService;

    @Test
    void apply_returnsSuccess() throws Exception {
        when(serviceRequestService.apply(any())).thenReturn(
                Map.of("request_id", 123456L, "status", "pending"));

        mockMvc.perform(post("/api/servicerequest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\",\"role_id\":\"2\",\"service_id\":\"3\",\"payment_multipy_factor\":{},\"rorinfo\":{},\"applicantinfo\":{} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Service request saved"))
                .andExpect(jsonPath("$.data.request_id").value(123456))
                .andExpect(jsonPath("$.data.status").value("pending"));
    }

    @Test
    void getRequests_returnsRequests() throws Exception {
        when(serviceRequestService.getRequests(any())).thenReturn(
                List.of(Map.of("request_id", 123456L, "status", "pending")));

        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\",\"role_id\":\"2\",\"request_for\":\"pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].request_id").value(123456))
                .andExpect(jsonPath("$.data[0].status").value("pending"));
    }

    @Test
    void getRequests_withPendingIdAlias_returnsRequests() throws Exception {
        org.mockito.ArgumentCaptor<com.jamipariseva.dto.request.RequestStatusRequest> captor = org.mockito.ArgumentCaptor.forClass(com.jamipariseva.dto.request.RequestStatusRequest.class);
        when(serviceRequestService.getRequests(captor.capture())).thenReturn(
                Map.of("request_id", 123456L, "status", "pending"));

        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\",\"role_id\":\"2\",\"request_for\":\"pending\",\"pending_id\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.request_id").value(123456))
                .andExpect(jsonPath("$.data.status").value("pending"));

        org.junit.jupiter.api.Assertions.assertEquals(123456L, captor.getValue().getRequestId());
    }

    @Test
    void getRequests_withList_returnsAllRequests() throws Exception {
        when(serviceRequestService.getRequests(any())).thenReturn(
                List.of(
                        Map.of("request_id", 123456L, "status", "pending"),
                        Map.of("request_id", 789012L, "status", "success")
                ));

        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\",\"role_id\":\"2\",\"request_for\":\"list\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].request_id").value(123456))
                .andExpect(jsonPath("$.data[0].status").value("pending"))
                .andExpect(jsonPath("$.data[1].request_id").value(789012))
                .andExpect(jsonPath("$.data[1].status").value("success"));
    }

    @Test
    void getRequests_missingFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void getAcknowledgement_returnsAcknowledgement() throws Exception {
        when(serviceRequestService.getAcknowledgement(eq("1"), eq("2"), eq(123456L))).thenReturn(
                Map.of("request_id", 123456L, "acknowledgement_no", "ACK-123"));

        mockMvc.perform(post("/api/acknowledgement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\",\"role_id\":\"2\",\"request_id\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.request_id").value(123456))
                .andExpect(jsonPath("$.data.acknowledgement_no").value("ACK-123"));
    }

    @Test
    void getAcknowledgement_missingFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/acknowledgement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void getDownloadUrl_returnsDownloadUrl() throws Exception {
        when(serviceRequestService.getDownloadUrl(eq("3"), eq("1"), eq("2"), eq(123456L))).thenReturn(
                Map.of("request_id", 123456L, "pdf_url", "http://example.com/pdf"));

        mockMvc.perform(post("/api/download")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"service_id\":\"3\",\"citizen_id\":\"1\",\"role_id\":\"2\",\"request_id\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.request_id").value(123456))
                .andExpect(jsonPath("$.data.pdf_url").value("http://example.com/pdf"));
    }


    @Test
    void getDownloadUrl_missingFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/download")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"service_id\":\"3\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void malformedJson_returnsBadRequestWithHttpCodes() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":")) 
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value(org.hamcrest.CoreMatchers.containsString("Malformed JSON")))
                .andExpect(jsonPath("$.httpCodes['400 Bad Request']").value("Invalid request/body format"))
                .andExpect(jsonPath("$.httpCodes['500 Internal Server Error']").value("Generic server error"));
    }

    @Test
    void validationFailed_returnsBadRequestWithHttpCodes() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"citizen_id\":\"1\"}")) 
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.httpCodes['400 Bad Request']").value("Invalid request/body format"))
                .andExpect(jsonPath("$.httpCodes['500 Internal Server Error']").value("Generic server error"));
    }
}
