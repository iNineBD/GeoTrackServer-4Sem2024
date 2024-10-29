package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored.MetricsResponse;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    public MetricsResponse getQtdSessionsAndMonitored(){

        // Query that returns the total number of active monitored
        Integer qtdMonitored = devicesRepository.qtdMonitoredAssets();

        // Query that returns the total number of active sessions
        Integer qtdSessions = geometryZoneRepository.qtdSessionsAssets();

        MetricsResponse response = new MetricsResponse(qtdMonitored,qtdSessions);

        return response;
    }
}
