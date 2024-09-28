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
import java.time.LocalDate;
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
}