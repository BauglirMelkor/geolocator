package com.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.StoreDTO;
import com.store.dto.json.MainObj;
import com.store.dto.json.StoreObj;
import com.store.persistence.entity.Store;
import com.store.persistence.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocatorService {

    @Autowired
    private StoreRepository storeRepository;


    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
           InputStream inputStream = new ClassPathResource("stores.json").getInputStream();
            MainObj obj = mapper.readValue(inputStream, MainObj.class);
            for (StoreObj storeObj : obj.getStores()) {
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(Double.parseDouble(storeObj.getLongitude()), Double.parseDouble(storeObj.getLatitude()));
                Store storeEntity = Store.builder().addressName(storeObj.getAddressName()).city(storeObj.getCity()).complexNumber(storeObj.getComplexNumber()).locationType(storeObj.getLocationType())
                        .sapStoreID(storeObj.getSapStoreID()).collectionPoint(storeObj.getCollectionPoint())
                        .postalCode(storeObj.getPostalCode()).showWarningMessage(storeObj.getShowWarningMessage()).street(storeObj.getStreet()).street2(storeObj.getStreet2()).street3(storeObj.getStreet3())
                        .todayClose(storeObj.getTodayClose()).uuid(storeObj.getUuid()).todayOpen(storeObj.getTodayOpen()).location(geoJsonPoint).build();
                storeRepository.save(storeEntity);

            }
            log.info("Stores were saved to the embedded database");

        } catch (Exception e) {
           log.error(e.getMessage());
        }
    }

    public List<StoreDTO> findClosestStores(Double longitute, Double latitude, Double dist, Integer size) {
        log.info("Searching for closest store");
        Distance distance = new Distance(dist, Metrics.KILOMETERS);
        PageRequest pageRequest = new PageRequest(0, size);
        List<GeoResult<Store>> storeList = storeRepository.findByLocationNear(new Point(longitute, latitude), distance, pageRequest).getContent();
        return storeList.stream().map(p -> convertToStoreDTO(p.getContent())).collect(Collectors.toList());
    }

    StoreDTO convertToStoreDTO(Store store) {
        return StoreDTO.builder().addressName(store.getAddressName()).city(store.getAddressName()).collectionPoint(store.getCollectionPoint())
                .complexNumber(store.getComplexNumber()).latitude(String.valueOf(store.getLocation().getY())).longitude(String.valueOf(store.getLocation().getX()))
                .locationType(store.getLocationType()).postalCode(store.getPostalCode()).sapStoreID(store.getSapStoreID())
                .uuid(store.getUuid())
                .street(store.getStreet()).street2(store.getStreet2()).street3(store.getStreet3())
                .showWarningMessage(store.getShowWarningMessage()).todayOpen(store.getTodayOpen()).todayClose(store.getTodayClose()).build();
    }
}
