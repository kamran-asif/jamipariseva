package com.jamipariseva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ror_record")
@Getter
@Setter
public class RorRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lgd_village_code")
    private String lgdVillageCode;

    @Column(name = "khatian_no")
    private String khatianNo;

    @Column(name = "plot_no")
    private String plotNo;

    @Column(name = "sfname")
    private String sfname;

    @Column(name = "slname")
    private String slname;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "total_share")
    private String totalShare;

    @Column(name = "mouja_name")
    private String moujaName;
}
