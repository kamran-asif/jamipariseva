package com.jamipariseva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Maps to db1.view_revenue_location_master in production.
 * For local dev, Hibernate creates revenue_location_master and data.sql seeds it.
 */
@Entity
@Table(name = "revenue_location_master")
@Getter
@Setter
public class RevenueLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idn")
    private String idn;

    @Column(name = "lgd_district_code")
    private String district;

    @Column(name = "dist_name_eng")
    private String districtName;

    @Column(name = "lgd_subdiv_code")
    private String subDivision;

    @Column(name = "subdiv_name_eng")
    private String subdivisionName;

    @Column(name = "lgd_circle_code")
    private String revenueCircle;

    @Column(name = "rsname_eng")
    private String revCircleName;

    @Column(name = "lgd_tehsil_code")
    private String tehsil;

    @Column(name = "tname_eng")
    private String tehsilName;

    @Column(name = "lgd_village_code")
    private String revenueMouza;

    @Column(name = "mouname_eng")
    private String moujaName;
}
