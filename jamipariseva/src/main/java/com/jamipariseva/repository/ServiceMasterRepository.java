package com.jamipariseva.repository;

import com.jamipariseva.entity.ServiceMasterEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceMasterRepository extends JpaRepository<ServiceMasterEntity, String> {

    List<ServiceMasterEntity> findByRoleIdAndActiveTrue(String roleId);

    boolean existsByServiceIdAndActiveTrue(String serviceId);
}
