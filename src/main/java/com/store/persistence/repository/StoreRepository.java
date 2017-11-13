package com.store.persistence.repository;


import com.store.persistence.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoPage;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StoreRepository extends MongoRepository<Store, String> {

    GeoPage<Store> findByLocationNear(Point location, Distance d, Pageable page);
}
