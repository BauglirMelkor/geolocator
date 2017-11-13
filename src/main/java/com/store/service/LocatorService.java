package com.store.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.StoreDTO;
import com.store.dto.json.MainObj;
import com.store.dto.json.StoreObj;
import com.store.persistence.entity.Store;
import com.store.persistence.repository.StoreRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocatorService {

    @Autowired
    private StoreRepository storeRepository;


    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new ClassPathResource("stores.json").getFile();
            MainObj obj = mapper.readValue(file, MainObj.class);
            for (StoreObj store : obj.getStores()) {
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()));
                Store storeEntity = Store.builder().addressName(store.getAddressName()).city(store.getCity()).complexNumber(store.getComplexNumber()).locationType(store.getLocationType())
                        .postalCode(store.getPostalCode()).showWarningMessage(store.getShowWarningMessage()).street(store.getStreet()).street2(store.getStreet2()).street3(store.getStreet3())
                        .todayClose(store.getTodayClose()).uuid(store.getUuid()).todayOpen(store.getTodayOpen()).location(geoJsonPoint).build();
                storeRepository.save(storeEntity);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<StoreDTO> findClosestStores(Double longitute, Double latitude, Double dist) {
        Distance distance = new Distance(dist, Metrics.KILOMETERS);
        PageRequest pageRequest = new PageRequest(0, 5);
        List<GeoResult<Store>> storeList = storeRepository.findByLocationNear(new Point(longitute, latitude), distance, pageRequest).getContent();
        return storeList.stream().map(p -> convertToStoreDTO(p.getContent())).collect(Collectors.toList());
    }

    StoreDTO convertToStoreDTO(Store store) {
        return StoreDTO.builder().addressName(store.getAddressName()).city(store.getAddressName()).collectionPoint(store.getCollectionPoint())
                .complexNumber(store.getComplexNumber()).latitude(String.valueOf(store.getLocation().getY())).longitude(String.valueOf(store.getLocation().getX()))
                .locationType(store.getLocationType()).postalCode(store.getPostalCode()).sapStoreID(store.getSapStoreID())
                .street(store.getStreet()).street2(store.getStreet2()).street3(store.getStreet3())
                .showWarningMessage(store.getShowWarningMessage()).todayOpen(store.getTodayOpen()).todayClose(store.getTodayClose()).build();
    }
}
