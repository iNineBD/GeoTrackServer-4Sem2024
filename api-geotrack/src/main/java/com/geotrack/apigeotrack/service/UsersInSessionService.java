package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.usersInSession.RequestUsersInSessionDTO;
import com.geotrack.apigeotrack.dto.usersInSession.ResponseUsersInSessionDTO;
import com.geotrack.apigeotrack.repositories.AssociationGeoLocRepository;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersInSessionService {

    @Autowired
    AssociationGeoLocRepository associationGeoLocRepository;

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    GeometryZoneRepository geometryZoneRepository;
    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Operation(summary = "Listar Usuários em Sessão", description = "Listar todos os usuários que tem algma localização dentro da sessão escolhida")
    public ResponseUsersInSessionDTO listUsersInSession(RequestUsersInSessionDTO requestUsersInSessionDTO) {

        List<Long> listDevices = associationGeoLocRepository.listDevices(requestUsersInSessionDTO.idSession());

        if (listDevices.isEmpty()) {
            throw new IllegalArgumentException("Não existem usuários dentro da sessão escolhida!");
        }

        ResponseUsersInSessionDTO responseUsersInSessionDTO = new ResponseUsersInSessionDTO(devicesRepository.listUsersInSession(listDevices));
        return responseUsersInSessionDTO;
    }
}
