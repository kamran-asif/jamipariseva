package com.jamipariseva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Khatian / RoR document record (maps to MSSQL khatian tables in production).
 */
@Entity
@Table(name = "khatian_record")
@Getter
@Setter
public class KhatianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lgd_village_code")
    private String lgdVillageCode;

    @Column(name = "khatian_no")
    private String khatianNo;

    @Column(name = "idn")
    private String idn;

    @Column(name = "ktsr")
    private String ktsr;

    @Lob
    @Column(name = "blob_data")
    private byte[] blobData;

    @Column(name = "file_formate")
    private String fileFormate;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "entered_by")
    private Integer enteredBy;

    @Column(name = "entry_ip")
    private String entryIp;

    @Column(name = "verified_by")
    private Integer verifiedBy;

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @Column(name = "verification_ip")
    private String verificationIp;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "approved_ip")
    private String approvedIp;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modification_ip")
    private String modificationIp;

    @Column(name = "kt1")
    private Integer kt1;

    @Column(name = "kt2")
    private Integer kt2;

    @Column(name = "is_cancelled")
    private String isCancelled;
}
