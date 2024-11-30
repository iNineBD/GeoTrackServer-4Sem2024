package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.insertdata.RequestInsert;
import com.geotrack.apigeotrack.dto.insertdata.RequestInsertGeo;
import com.geotrack.apigeotrack.entities.GeoLocation;
import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.entities.User;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.GeoLocationRepository;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.repositories.UserRepository;
import com.geotrack.apigeotrack.service.utils.GeometryUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InsertDataService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    DevicesRepository devicesRepository;

    @Operation(summary = "Inserir dados de localização", description = "Inserir dados de localização no banco de dados")
    @Transactional
    public void insertDataService(List<RequestInsert> requestInserts) throws Exception {
        // Coleta todos os IDs de usuários
        Set<Integer> userIds = requestInserts.stream()
                .map(requestInsert -> Integer.valueOf(requestInsert.idUser()))
                .collect(Collectors.toSet());

        // Busca todos os usuários em uma única consulta
        List<User> existingUsers = userRepository.findAllById(userIds);
        if (existingUsers.size() != userIds.size()) {
            throw new NoSuchElementException("Um ou mais usuários não encontrados");
        }

        // Mapeia os usuários para um mapa para acesso rápido
        Map<Integer, User> userMap = existingUsers.stream()
                .collect(Collectors.toMap(User::getIdUser, Function.identity()));

        List<Location> listAll = new ArrayList<>();
        for (RequestInsert requestInsert : requestInserts) {
            User user = userMap.get(Integer.valueOf(requestInsert.idUser()));
            Location location = new Location();

            location.setDevices(user.getDevices().getFirst());
            location.setDateTime(Timestamp.valueOf(requestInsert.dateTime()));
            location.setDateReferences(requestInsert.dateTime().toLocalDate());
            location.setLatitude(requestInsert.latitude());
            location.setLongitude(requestInsert.longitude());

            listAll.add(location);
        }

        // Salva todos os locais de uma vez
        locationRepository.saveAll(listAll);
    }

    @Autowired
    GeoLocationRepository geoLocationRepository;

    @Operation(summary = "Inserir dados de localização", description = "Inserir dados de localização no banco de dados")
    @Transactional
    public void insertDataGeoService(List<RequestInsertGeo> requestInsertGeo) throws Exception {

        // Coleta todos os IDs de usuários
        Set<Integer> userIds = requestInsertGeo.stream()
                .map(requestInsert -> Integer.valueOf(requestInsert.idUser()))
                .collect(Collectors.toSet());

        // Busca todos os usuários em uma única consulta
        List<User> existingUsers = userRepository.findAllById(userIds);
        if (existingUsers.size() != userIds.size()) {
            throw new NoSuchElementException("Um ou mais usuários não encontrados");
        }

        // Mapeia os usuários para um mapa para acesso rápido
        Map<Integer, User> userMap = existingUsers.stream()
                .collect(Collectors.toMap(User::getIdUser, Function.identity()));

        List<GeoLocation> listAll = new ArrayList<>();
        for (RequestInsertGeo requestInsert : requestInsertGeo) {

            User user = userMap.get(Integer.valueOf(requestInsert.idUser()));
            GeoLocation location = new GeoLocation();

            // Criar geometria
            Point geometry = GeometryUtils.criarGeometry(requestInsert.latitude(), requestInsert.longitude());
            location.setGeometry(geometry);

            // Configura o dispositivo (presumindo que 'user.getDevices().getFirst()' está correto)
            location.setDevices(user.getDevices().getFirst());

            // Configura a data e hora
            location.setDateTime(requestInsert.dateTime());

            // Configura a data de referência
            location.setDateReferences(requestInsert.dateTime().toLocalDate());

            geoLocationRepository.insertGeoLocation(requestInsert.longitude(), requestInsert.latitude(), requestInsert.dateTime(), requestInsert.dateTime().toLocalDate(), user.getDevices().getFirst().getIdDevices());

        }

    }
}