package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.insertdata.RequestInsert;
import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.entities.User;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class InsertDataService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    DevicesRepository devicesRepository;

    @Transactional
    public void insertDataService(List<RequestInsert> requestInserts) throws Exception {
        List<Location> listAll = new ArrayList<>();
        for (RequestInsert requestInsert : requestInserts) {

            Optional<User> existingUser = userRepository.findById(Integer.valueOf(requestInsert.idUser()));

            if (existingUser.isEmpty()) {
                throw new NoSuchElementException("Nenhum usuario encontrado");
            }

            Location location = new Location();
            location.setDevices(existingUser.get().getDevices().getFirst());
            location.setDateTime(Timestamp.valueOf(requestInsert.dateTime()));
            location.setLatitude(requestInsert.latitude());
            location.setLongitude(requestInsert.longitude());

            listAll.add(location);
        }

        locationRepository.saveAll(listAll);
    }
}