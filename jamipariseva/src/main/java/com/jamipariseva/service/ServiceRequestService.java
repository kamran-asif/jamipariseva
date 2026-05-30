package com.jamipariseva.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamipariseva.dto.request.ApplyServiceRequestDto;
import com.jamipariseva.dto.request.RequestStatusRequest;
import com.jamipariseva.entity.ServiceRequestEntity;
import com.jamipariseva.exception.BadRequestException;
import com.jamipariseva.exception.ResourceNotFoundException;
import com.jamipariseva.repository.ServiceMasterRepository;
import com.jamipariseva.repository.ServiceRequestRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private static final String STATUS_PENDING = "pending";
    private static final String STATUS_SUCCESS = "success";

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceMasterRepository serviceMasterRepository;
    private final ObjectMapper objectMapper;

    public Map<String, Object> apply(ApplyServiceRequestDto dto) {
        if (!serviceMasterRepository.existsByServiceIdAndActiveTrue(dto.getServiceId())) {
            throw new BadRequestException("Invalid or inactive service_id: " + dto.getServiceId());
        }
        try {
            String requestId = "REQ" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
            ServiceRequestEntity entity = new ServiceRequestEntity();
            entity.setRequestId(requestId);
            entity.setCitizenId(dto.getCitizenId());
            entity.setRoleId(dto.getRoleId());
            entity.setServiceId(dto.getServiceId());
            entity.setStatus(STATUS_PENDING);
            entity.setAcknowledgementNo("ACK-" + requestId);
            entity.setRequestPayload(objectMapper.writeValueAsString(dto));
            serviceRequestRepository.save(entity);

            Map<String, Object> result = new HashMap<>();
            result.put("request_id", requestId);
            result.put("status", STATUS_PENDING);
            result.put("acknowledgement_no", entity.getAcknowledgementNo());
            result.put("message", "Service request saved successfully");
            return result;
        } catch (Exception ex) {
            throw new BadRequestException("Failed to save service request: " + ex.getMessage());
        }
    }

    public Object getRequests(RequestStatusRequest request) {
        String status = normalizeStatus(request.getRequestFor());
        if (StringUtils.hasText(request.getRequestId())) {
            ServiceRequestEntity entity = serviceRequestRepository
                    .findByRequestIdAndCitizenIdAndRoleId(
                            request.getRequestId(), request.getCitizenId(), request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
            if (!status.equalsIgnoreCase(entity.getStatus())) {
                throw new ResourceNotFoundException("Request not found for status: " + request.getRequestFor());
            }
            return toDetailMap(entity);
        }
        return serviceRequestRepository
                .findByCitizenIdAndRoleIdAndStatusOrderByCreatedAtDesc(
                        request.getCitizenId(), request.getRoleId(), status)
                .stream()
                .map(this::toSummaryMap)
                .toList();
    }

    public Map<String, Object> getAcknowledgement(String citizenId, String roleId, String requestId) {
        ServiceRequestEntity entity = findRequest(citizenId, roleId, requestId);
        Map<String, Object> ack = new HashMap<>();
        ack.put("request_id", entity.getRequestId());
        ack.put("acknowledgement_no", entity.getAcknowledgementNo());
        ack.put("citizen_id", entity.getCitizenId());
        ack.put("service_id", entity.getServiceId());
        ack.put("status", entity.getStatus());
        ack.put("submitted_at", entity.getCreatedAt());
        return ack;
    }

    public Map<String, Object> getDownloadUrl(String serviceId, String citizenId, String roleId, String requestId) {
        ServiceRequestEntity entity = findRequest(citizenId, roleId, requestId);
        if (!serviceId.equals(entity.getServiceId())) {
            throw new BadRequestException("service_id does not match the request");
        }
        if (!STATUS_SUCCESS.equalsIgnoreCase(entity.getStatus())) {
            throw new BadRequestException("PDF is available only for successful requests");
        }
        if (!StringUtils.hasText(entity.getPdfUrl())) {
            throw new ResourceNotFoundException("PDF URL not found for this request");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("request_id", entity.getRequestId());
        result.put("pdf_url", entity.getPdfUrl());
        return result;
    }

    /** Marks a request as success and sets PDF URL — for testing / admin flow. */
    public Map<String, Object> markSuccess(String requestId, String pdfUrl) {
        ServiceRequestEntity entity = serviceRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        entity.setStatus(STATUS_SUCCESS);
        entity.setPdfUrl(pdfUrl);
        entity.setUpdatedAt(LocalDateTime.now());
        serviceRequestRepository.save(entity);
        return toDetailMap(entity);
    }

    private ServiceRequestEntity findRequest(String citizenId, String roleId, String requestId) {
        return serviceRequestRepository
                .findByRequestIdAndCitizenIdAndRoleId(requestId, citizenId, roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
    }

    private String normalizeStatus(String requestFor) {
        String value = requestFor.trim().toLowerCase();
        if (!STATUS_PENDING.equals(value) && !STATUS_SUCCESS.equals(value)) {
            throw new BadRequestException("request_for must be pending or success");
        }
        return value;
    }

    private Map<String, Object> toSummaryMap(ServiceRequestEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("request_id", entity.getRequestId());
        map.put("service_id", entity.getServiceId());
        map.put("status", entity.getStatus());
        map.put("created_at", entity.getCreatedAt());
        return map;
    }

    private Map<String, Object> toDetailMap(ServiceRequestEntity entity) {
        Map<String, Object> map = toSummaryMap(entity);
        map.put("acknowledgement_no", entity.getAcknowledgementNo());
        map.put("pdf_url", entity.getPdfUrl());
        map.put("request_payload", entity.getRequestPayload());
        map.put("updated_at", entity.getUpdatedAt());
        return map;
    }
}
