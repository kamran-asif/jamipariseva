package com.jamipariseva.repository;

import com.jamipariseva.entity.KhatianEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhatianRepository extends JpaRepository<KhatianEntity, Long> {

    Optional<KhatianEntity> findByLgdVillageCodeAndKhatianNo(String lgdVillageCode, String khatianNo);
}
