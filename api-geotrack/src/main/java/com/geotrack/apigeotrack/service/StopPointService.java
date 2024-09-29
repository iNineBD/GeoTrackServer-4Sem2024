package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
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

@Service
public class StopPointService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    GeoRedisServices geoRedisService;

    @Cacheable(value = "stoppingPoints", key = "#requestDTO")
    public List<LocalizacaoDTO> findStopPointByDeviceAndData(StopPointRequestDTO requestDTO) {

        List<StopPointDBDTO> listStop = UtilsServices.convertToStopPointDTO(locationRepository.findLocalizationGroupedByDateWithInterval(requestDTO.device(), requestDTO.startDate(), requestDTO.finalDate()));
        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        List<LocalizacaoDTO> stopPoints = new ArrayList<>();

        for (StopPointDBDTO stopPointDBDTO : listStop) {
            LocalizacaoDTO point = toExecStopPoint(stopPointDBDTO, stopPoints);
            if (point != null) {
                stopPoints.add(point);
            }
        }

        if (stopPoints.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        return (stopPoints);
    }

    public LocalizacaoDTO toExecStopPoint(StopPointDBDTO in, List<LocalizacaoDTO> stopPoints) {

        if (!stopPoints.isEmpty()) {
            for (LocalizacaoDTO localizacaoDTO : stopPoints) {
                if (localizacaoDTO.latitude().equals(in.latitude()) && localizacaoDTO.longitude().equals(in.longitude())){
                    return null;
                }
            }
        }

        String idRegister = in.startDate().toString() + in.endDate().toString() + in.latitude() + in.longitude();

        geoRedisService.addLocation(idRegister, in.latitude(), in.longitude(), "pontoMedio");

        String[] coordinates= in.latLongList().split("\\|");

        for(int i = 0 ; i < coordinates.length ; i++) {
            String[] latLongArray = coordinates[i].split(";");
            BigDecimal latitude = new BigDecimal(latLongArray[0].replace(",", "."));
            BigDecimal longitude = new BigDecimal(latLongArray[1].replace(",", "."));

            geoRedisService.addLocation(idRegister, latitude, longitude, "p"+i);

            Distance distance = geoRedisService.calculateDistance(idRegister, "pontoMedio", "p"+i);
            geoRedisService.removeLocation(idRegister, "p"+i);

            if (distance.getValue() > 8){
                return null;
            }
        }
        geoRedisService.removeLocation(idRegister, "pontoMedio");
        return new LocalizacaoDTO(in.latitude(), in.longitude());
    }



    public List<FeatureDTO> resquestGeoJson(List<LocalizacaoDTO> stopPoints) {

        List<FeatureDTO> feature = new ArrayList<>(stopPoints.size());

        for (LocalizacaoDTO point : stopPoints) {

            BigDecimal[] listCoordenates = {point.latitude(),point.longitude()};

            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);

            feature.add(new FeatureDTO("Feature",new PropertiesDTO(), geometry));
        }
        return feature;
    }
}
