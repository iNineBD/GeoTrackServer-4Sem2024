package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored.MetricsResponse;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    @Autowired
    LoginRepository loginRepository;

    public MetricsResponse getQtdSessionsAndMonitored(){

        // Query that returns the total number of active monitored
        Integer qtdMonitored = devicesRepository.qtdMonitoredAssets();

        // Query that returns the total number of active sessions
        Integer qtdSessions = geometryZoneRepository.qtdSessionsAssets();

        // Query that returns the total number of people registered in the system
        Integer qtdUsers = loginRepository.getQtdUsers();

        MetricsResponse response = new MetricsResponse(qtdMonitored,qtdSessions,qtdUsers);

        return response;
    }
}
