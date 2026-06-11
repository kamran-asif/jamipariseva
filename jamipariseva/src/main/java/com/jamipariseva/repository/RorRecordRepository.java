package com.jamipariseva.repository;

import com.jamipariseva.entity.RorRecordEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RorRecordRepository extends JpaRepository<RorRecordEntity, Long> {

    Optional<RorRecordEntity> findByLgdVillageCodeAndKhatianNo(String lgdVillageCode, String khatianNo);

    Optional<RorRecordEntity> findByLgdVillageCodeAndPlotNo(String lgdVillageCode, String plotNo);

    Optional<RorRecordEntity> findByLgdVillageCodeAndOwnerNameIgnoreCase(
            String lgdVillageCode, String ownerName);
}
