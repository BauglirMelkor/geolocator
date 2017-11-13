package com.store.persistence.entity;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
public class Store {

    @Id
    private String uuid;

    private String city;

    private String postalCode;

    private String street;

    private String street2;

    private String street3;

    private String addressName;

    private String complexNumber;

    private Boolean showWarningMessage;

    private String todayOpen;

    private String locationType;

    private String todayClose;

    private Boolean collectionPoint;

    private String sapStoreID;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
}
