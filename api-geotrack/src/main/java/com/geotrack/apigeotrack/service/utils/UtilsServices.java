package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Component
//public class UtilsServices {
//
//    @Autowired
//    LocationRepository locationRepository;
//
//    public List<Location> getLocations(Long idDev, LocalDate startDate, LocalDate finalDate) {
//
//        List<Location> locations = new ArrayList<>();
//        PageRequest pageRequest = PageRequest.of(0, 800);
//
//        Page<Location> locationsInPage = locationRepository.findLocalizationByDataReferencePagination(idDev, startDate, finalDate, pageRequest);
//        while (!locationsInPage.isEmpty()){
//            locations.addAll(locationsInPage.getContent());
//            pageRequest.next();
//            locationsInPage = locationRepository.findLocalizationByDataReferencePagination(idDev, startDate, finalDate, pageRequest);
//        }
//        return locations;
//    }
//
//}
