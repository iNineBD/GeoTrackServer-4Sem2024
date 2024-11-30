package com.geotrack.apigeotrack.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.geotrack.apigeotrack.dto.insertdata.RequestInsert;
import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.entities.User;
import com.geotrack.apigeotrack.repositories.GeoLocationRepository;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PopulateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    GeoLocationRepository geoLocationRepository;

    @Operation(summary = "Popula o banco de dados", description = "Popula o banco de dados com os dados do arquivo")
    @Transactional
    public void populate(String pathToFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        JsonParser jsonParser = new JsonFactory().createParser(new File(pathToFile));
        jsonParser.nextToken();

        List<Location> insertAll = new ArrayList<>();
        while (jsonParser.nextToken() != null) {
            RequestInsert requestInsert = objectMapper.readValue(jsonParser, RequestInsert.class);

            if (requestInsert == null) {
                break;
            }

            Optional<User> user = userRepository.findById(Integer.valueOf(requestInsert.idUser()));

            if (user.isEmpty()){
                System.out.println("User not found: " + requestInsert.idUser());
                continue;
            }

            if (user.get().getDevices().isEmpty()) {
                System.out.println("Dispositivos not found para usuario: " + requestInsert.idUser());
                continue;
            }

            Location location = new Location(requestInsert.idCustomerBase(), requestInsert.longitude(), requestInsert.latitude(), requestInsert.dateTime(), user.get().getDevices().getFirst());

            if (location.getIdLocation().isEmpty()){
                System.out.println("IDLocal not found para usuario: " + requestInsert.idUser());
                continue;
            }

//            insertAll.add(location);

            geoLocationRepository.insertGeoLocation(requestInsert.longitude(), requestInsert.latitude(), requestInsert.dateTime(), requestInsert.dateTime().toLocalDate(), user.get().getDevices().getFirst().getIdDevices());
            System.out.println("Inserido: " + requestInsert.idCustomerBase());
//            if (insertAll.size() >= 500){
//                LocalDateTime agora = LocalDateTime.now();
//                locationRepository.saveAll(insertAll);
//                System.out.println("Levou: " + Duration.between(agora, LocalDateTime.now()).toString());
//                insertAll.clear();
//            }
        }

        jsonParser.close();
//        locationRepository.saveAll(insertAll);
    }
}
