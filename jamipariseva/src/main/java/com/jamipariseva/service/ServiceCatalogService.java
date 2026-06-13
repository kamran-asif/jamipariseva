package com.jamipariseva.service;

import com.jamipariseva.dto.service.CitizenServiceRequest;
import com.jamipariseva.dto.service.ServiceItemDto;
import com.jamipariseva.repository.ServiceMasterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCatalogService {

    private final ServiceMasterRepository serviceMasterRepository;

    public List<ServiceItemDto> getServices(CitizenServiceRequest request) {
        return serviceMasterRepository.findByRoleIdAndActiveTrue(request.getRoleId()).stream()
                .map(s -> ServiceItemDto.builder()
                        .serviceId(s.getServiceId())
                        .serviceName(s.getServiceName())
                        .description(s.getDescription())
                        .feeAmount(s.getFeeAmount())
                        .servicePath(s.getServicePath())
                        .build())
                .toList();
    }
}
