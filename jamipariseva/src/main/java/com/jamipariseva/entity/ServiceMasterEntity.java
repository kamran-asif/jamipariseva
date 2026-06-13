package com.jamipariseva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_master")
@Getter
@Setter
public class ServiceMasterEntity {

    @Id
    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "description")
    private String description;

    @Column(name = "fee_amount")
    private Double feeAmount;

    @Column(name = "is_active")
    private Boolean active = true;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "service_path")
    private String servicePath;
}
