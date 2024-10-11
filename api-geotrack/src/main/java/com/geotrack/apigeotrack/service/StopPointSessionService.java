package com.geotrack.apigeotrack.service;


import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import com.geotrack.apigeotrack.dto.stopoint.StopPointDBDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class StopPointSessionService {

    @Autowired
    LocationRepository locationRepository;

    private static final double EARTH_RADIUS = 6371000;

    @Autowired
    StopPointService stopService;

    public StopPointSessionResponseDTO stopPointInSession(StopPointSessionRequestDTO stopPointSessionRequestDTO) {

        List<LocalizacaoDTO> stopPoints = findStopPointByDeviceAndData(stopPointSessionRequestDTO);

        List<LocalizacaoDTO> stopPointsInSession = new ArrayList<>();

        for (LocalizacaoDTO localizacao : stopPoints) {
            double distance = haversine(stopPointSessionRequestDTO, localizacao.latitude().doubleValue(), localizacao.longitude().doubleValue());
            if (distance <= stopPointSessionRequestDTO.radius()) {
                stopPointsInSession.add(localizacao);
            }
        }

        StopPointSessionResponseDTO stopPointSessionResponseDTO = new StopPointSessionResponseDTO(stopPointsInSession);
        return stopPointSessionResponseDTO;
    }

    public List<LocalizacaoDTO> findStopPointByDeviceAndData(StopPointSessionRequestDTO stopPointSessionRequestDTO) {

        List<StopPointDBDTO> listStop = UtilsServices.convertToStopPointDTO(locationRepository.findOneLocalizationGroupedByDateWithInterval(stopPointSessionRequestDTO.deviceId(), stopPointSessionRequestDTO.startDate(), stopPointSessionRequestDTO.endDate()));
        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        List<LocalizacaoDTO> stopPoints = new ArrayList<>();

        for (StopPointDBDTO stopPointDBDTO : listStop) {
            LocalizacaoDTO point = stopService.toExecStopPoint(stopPointDBDTO, stopPoints);
            if (point != null) {
                stopPoints.add(point);
            }
        }

        if (stopPoints.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        return (stopPoints);
    }


    public static double haversine(StopPointSessionRequestDTO stopPointSessionRequestDTO, double lat2, double lng2) {
        double lat1 = stopPointSessionRequestDTO.coordinates().latitude().doubleValue();
        double lng1 = stopPointSessionRequestDTO.coordinates().longitude().doubleValue();

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
