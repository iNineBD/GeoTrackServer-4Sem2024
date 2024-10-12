package com.geotrack.apigeotrack.service;


import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import com.geotrack.apigeotrack.dto.stopoint.StopPointDBDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.GeoRedisServices;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class StopPointSessionService {

    @Autowired
    LocationRepository locationRepository;

    private static final double EARTH_RADIUS = 6371000;

    @Autowired
    StopPointService stopService;

    @Autowired
    GeoRedisServices geoRedisService;

    // This function returns all Stop Point that are in a Geographic Session
    @Cacheable(value = "stoppingPoints", key = "#stopPointSessionRequestDTO")
    public StopPointSessionResponseDTO stopPointInSession(StopPointSessionRequestDTO stopPointSessionRequestDTO) {

        List<LocalizacaoDTO> stopPointsInSession = findStopPointInSessionByDeviceAndData(stopPointSessionRequestDTO);

        // return list
        return new StopPointSessionResponseDTO(stopPointsInSession);
    }

    // This function return a List from the database that bring the stop points referent a device
    public List<LocalizacaoDTO> findStopPointInSessionByDeviceAndData(StopPointSessionRequestDTO stopPointSessionRequestDTO) {

        List<StopPointDBDTO> listStop = UtilsServices.convertToStopPointDTO(
                locationRepository.findStopPointsByUsersAndSession(
                        stopPointSessionRequestDTO.deviceId(),
                        stopPointSessionRequestDTO.startDate(),
                        stopPointSessionRequestDTO.endDate()
                )
        );

        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        String uuidRedis = "stopPointInSession" + UUID.randomUUID().toString();

        // add avg lat and long in cache
        geoRedisService.addLocation(uuidRedis, stopPointSessionRequestDTO.coordinates().latitude(), stopPointSessionRequestDTO.coordinates().longitude(), "centerSession");

        List<LocalizacaoDTO> stopPoints = new ArrayList<>();
        for (StopPointDBDTO stopPointDBDTO : listStop) {
            LocalizacaoDTO point = toExecStopPointInSession(stopPointDBDTO, stopPoints, stopPointSessionRequestDTO.radius(), uuidRedis);
            if (point != null) {
                stopPoints.add(point);
            }
        }

        geoRedisService.removeLocation(uuidRedis, "centerSession");

        if (stopPoints.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        return (stopPoints);
    }


    public LocalizacaoDTO toExecStopPointInSession(StopPointDBDTO stopPointToCheck, List<LocalizacaoDTO> stopPointsInSession, double radiusMeters, String idRegisterRedis) {

        // validates if the coordinate has already been inserted in the list
        if (UtilsServices.checkStopPointDuplicate(stopPointToCheck, stopPointsInSession)) return null;

        // add avg lat and long in cache
        geoRedisService.addLocation(idRegisterRedis, stopPointToCheck.latitude(), stopPointToCheck.longitude(), "pontoMedio");

        Distance distanceToCenterSession = geoRedisService.calculateDistance(idRegisterRedis, "centerSession", "pontoMedio");

        if (distanceToCenterSession.getValue() > radiusMeters) return null;

        // db return a list of lat and long. Here we separate this
        String[] coordinates= stopPointToCheck.latLongList().split("\\|");
        for(int i = 0 ; i < coordinates.length ; i++) {

            // bd return a "map" to lat e long. Here we separate this
            String[] latLongArray = coordinates[i].split(";");
            BigDecimal latitude = new BigDecimal(latLongArray[0].replace(",", "."));
            BigDecimal longitude = new BigDecimal(latLongArray[1].replace(",", "."));

            // add px in cache
            geoRedisService.addLocation(idRegisterRedis, latitude, longitude, "p"+i);

            // calcule the distance between "pontoMedio" and px
            Distance distanceToAvg = geoRedisService.calculateDistance(idRegisterRedis, "pontoMedio", "p"+i);

            // remove px from cache
            geoRedisService.removeLocation(idRegisterRedis, "p"+i);

            //verify if the distance between px and pontoMedio is greater than 8 meters
            if (distanceToAvg.getValue() > 8) return null;

        }
        // remove pontoMedio from cache
        geoRedisService.removeLocation(idRegisterRedis, "pontoMedio");

        return new LocalizacaoDTO(stopPointToCheck.latitude(), stopPointToCheck.longitude());
    }
}
