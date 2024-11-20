package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.insertdata.RequestInsertGeo;
import com.geotrack.apigeotrack.entities.GeoLocation;
import com.geotrack.apigeotrack.entities.User;
import com.geotrack.apigeotrack.repositories.InsertDataGeoRepository;
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
public class InsertDataGeoService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    InsertDataGeoRepository insertDataGeoRepository;

    @Operation(summary = "Inserir dados de localização", description = "Inserir dados de localização no banco de dados")
    @Transactional
    public void insertDataService(List<RequestInsertGeo> requestInsertGeo) throws Exception {
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
            location.setDateTime(Timestamp.valueOf(requestInsert.dateTime()));

            // Configura a data de referência
            location.setDateReferences(requestInsert.dateTime().toLocalDate());

            listAll.add(location);
        }

        // Salva todos os locais de uma vez
        insertDataGeoRepository.saveAll(listAll);
    }
}
