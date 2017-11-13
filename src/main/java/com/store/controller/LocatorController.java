package com.store.controller;

import com.store.dto.StoreDTO;
import com.store.exception.LocationException;
import com.store.service.LocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/locator")
@RequiredArgsConstructor
public class LocatorController {

    @Autowired
    private LocatorService locatorService;

    @RequestMapping(method = RequestMethod.GET, value = "/{long}/{lat}/{dist}/{size}")
    public ResponseEntity<List<StoreDTO>> matchDetail(@PathVariable(value = "long", required = true) Double longitute,
                                                      @PathVariable(value = "lat", required = true) Double latitute,
                                                      @PathVariable(value = "dist", required = false) Double distance,
                                                      @PathVariable(value = "size", required = false) Integer size
    ) throws LocationException {
        if (distance == -1) {
            distance = 500000d;
        }
        if (size == null || size == -1) {
            size = 5;
        }
        if (longitute == -181 || latitute == -91) {
            throw new LocationException("Please Provide Longitude and Latitude Exception");
        }
        try {
            return ResponseEntity.ok().body(locatorService.findClosestStores(longitute, latitute, distance, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
