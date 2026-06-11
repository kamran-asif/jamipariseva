package com.jamipariseva.repository;

import com.jamipariseva.entity.ServiceRequestEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, Long> {

    List<ServiceRequestEntity> findByCitizenIdAndRoleIdAndStatusOrderByCreatedAtDesc(
            String citizenId, String roleId, String status);

    Optional<ServiceRequestEntity> findByRequestIdAndCitizenIdAndRoleId(
            Long requestId, String citizenId, String roleId);
}
