package com.jamipariseva.repository;

/**
 * Spring Data projection for distinct location query results.
 */
public interface LocationProjection {

    String getCode();

    String getNameEng();
}
