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

    @Column(name = "lgd_dist_code")
    private String lgdDistCode;

    @Column(name = "dist_name_eng")
    private String distNameEng;

    @Column(name = "lgd_subdiv_code")
    private String lgdSubdivCode;

    @Column(name = "subdiv_name_eng")
    private String subdivNameEng;

    @Column(name = "lgd_circle_code")
    private String lgdCircleCode;

    @Column(name = "rsname_eng")
    private String rsnameEng;

    @Column(name = "lgd_tehsil_code")
    private String lgdTehsilCode;

    @Column(name = "tname_eng")
    private String tnameEng;

    @Column(name = "lgd_village_code")
    private String lgdVillageCode;

    @Column(name = "mouname_eng")
    private String mounameEng;
}
