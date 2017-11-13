package com.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.StoreObj;
import com.store.dto.Stores;
import com.store.persistence.entity.Store;
import com.store.persistence.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Service
public class LocatorService {

    @Autowired
    private StoreRepository storeRepository;


    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new ClassPathResource("stores.json").getFile();
            StoreObj obj = mapper.readValue(file, StoreObj.class);
            for (Stores store : obj.getStores()) {
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()));
                Store storeEntity = Store.builder().addressName(store.getAddressName()).city(store.getCity()).complexNumber(store.getComplexNumber()).locationType(store.getLocationType())
                        .postalCode(store.getPostalCode()).showWarningMessage(store.getShowWarningMessage()).street(store.getStreet()).street2(store.getStreet2()).street3(store.getStreet3())
                        .todayClose(store.getTodayClose()).uuid(store.getUuid()).todayOpen(store.getTodayOpen()).location(geoJsonPoint).build();
                storeRepository.save(storeEntity);

            }

            Store se = storeRepository.findOne("WXIKYx4XBRYAAAFIIgoYwKxK");
            System.out.println("STORE: " + se.getAddressName());
            Distance distance = new Distance(500000d, Metrics.KILOMETERS);
            PageRequest pageRequest = new PageRequest(0,5);
           List<GeoResult<Store>> storeList = storeRepository.findByLocationNear(new Point(0, 0), distance,pageRequest).getContent();
                for(GeoResult<Store> s :    storeList) {
                    System.out.println("STORE: " + s.getContent().getAddressName());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
