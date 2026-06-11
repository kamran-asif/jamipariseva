package com.jamipariseva.repository;

import com.jamipariseva.entity.RevenueLocationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RevenueLocationRepository extends JpaRepository<RevenueLocationEntity, Long> {

    @Query("""
            SELECT DISTINCT r.district AS code, r.districtName AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.district IS NOT NULL
            ORDER BY r.districtName
            """)
    List<LocationProjection> getDistricts();

    @Query("""
            SELECT DISTINCT r.subDivision AS code, r.subdivisionName AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.district = :distCode AND r.subDivision IS NOT NULL
            ORDER BY r.subdivisionName
            """)
    List<LocationProjection> getSubDivisions(@Param("distCode") String distCode);

    @Query("""
            SELECT DISTINCT r.revenueCircle AS code, r.revCircleName AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.subDivision = :subdivCode AND r.revenueCircle IS NOT NULL
            ORDER BY r.revCircleName
            """)
    List<LocationProjection> getCircles(@Param("subdivCode") String subdivCode);

    @Query("""
            SELECT DISTINCT r.tehsil AS code, r.tehsilName AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.revenueCircle = :circleCode AND r.tehsil IS NOT NULL
            ORDER BY r.tehsilName
            """)
    List<LocationProjection> getTehsils(@Param("circleCode") String circleCode);

    @Query("""
            SELECT DISTINCT r.revenueMouza AS code, r.moujaName AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.tehsil = :tehsilCode AND r.revenueMouza IS NOT NULL
            ORDER BY r.moujaName
            """)
    List<LocationProjection> getVillages(@Param("tehsilCode") String tehsilCode);
}
