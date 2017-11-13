package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Stores {

    @JsonProperty("city")
    private String city;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("street")
    private String street;
    @JsonProperty("street2")
    private String street2;
    @JsonProperty("street3")
    private String street3;
    @JsonProperty("addressName")
    private String addressName;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("complexNumber")
    private String complexNumber;
    @JsonProperty("showWarningMessage")
    private Boolean showWarningMessage;
    @JsonProperty("todayOpen")
    private String todayOpen;
    @JsonProperty("locationType")
    private String locationType;
    @JsonProperty("todayClose")
    private String todayClose;
    @JsonProperty("collectionPoint")
    private Boolean collectionPoint;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("sapStoreID")
    private String sapStoreID;



}
