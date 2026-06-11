package com.jamipariseva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_request")
@Getter
@Setter
public class ServiceRequestEntity {

    @Id
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "status")
    private String status;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "acknowledgement_no")
    private String acknowledgementNo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }
}
