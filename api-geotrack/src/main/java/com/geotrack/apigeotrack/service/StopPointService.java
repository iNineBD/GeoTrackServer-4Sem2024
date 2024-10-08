package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Devices;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.GeoRedisServices;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StopPointService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    GeoRedisServices geoRedisService;

    @Cacheable(value = "stoppingPoints", key = "#requestDTO")
    public List<StopPointResponseDTO> findStopPointByDeviceAndData(StopPointRequestDTO requestDTO) {

        if(requestDTO.devices().size() > 5){
            throw new IllegalArgumentException("Limite de dispositivos excedido. Máximo de 5 por consulta");
        }

        List<StopPointResponseDTO> deviceGeoJsonList = new ArrayList<>();
        List<StopPointDBDTO> listStop = UtilsServices.convertToStopPointDTO(locationRepository.findLocalizationGroupedByDateWithInterval(requestDTO.devices(), requestDTO.startDate(), requestDTO.finalDate())
        );

        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada para os dispositivos. ");
        }

            List<LocalizacaoDTO> stopPoints = new ArrayList<>();
            Integer idPrevious = listStop.get(0).idDev();

            int i = 0;
            for (StopPointDBDTO stopPointDBDTO : listStop) {
                Integer idCurrent = stopPointDBDTO.idDev();
                if(!idCurrent.equals(idPrevious) || i == listStop.size() -1){
                    if (!stopPoints.isEmpty()) {
                        Optional<Devices> device = devicesRepository.findById(idPrevious);
                        String userName = device.get().getUser().getName().toUpperCase();
                        String nameDevice = device.get().getCode().toUpperCase();

                        List<FeatureDTO> feature = resquestGeoJson(stopPoints);
                        GeoJsonDTO geoJson = new GeoJsonDTO("FeatureCollection",feature);
                        deviceGeoJsonList.add(new StopPointResponseDTO(userName,nameDevice, geoJson));
                    }
                    idPrevious = idCurrent;
                    stopPoints.clear();
                }
                LocalizacaoDTO point = toExecStopPoint(stopPointDBDTO, stopPoints);
                if (point != null) {
                    stopPoints.add(point);
                }

                i++;
            }

        return deviceGeoJsonList;
    }


    public LocalizacaoDTO toExecStopPoint(StopPointDBDTO in, List<LocalizacaoDTO> stopPoints) {

        if (!stopPoints.isEmpty()) {
            BigDecimal scaledInLatitude = in.latitude().setScale(2, RoundingMode.HALF_UP);
            BigDecimal scaledInLongitude = in.longitude().setScale(2, RoundingMode.HALF_UP);

            for (LocalizacaoDTO localizacaoDTO : stopPoints) {
                BigDecimal scaledStopLatitude = localizacaoDTO.latitude().setScale(2, RoundingMode.HALF_UP);
                BigDecimal scaledStopLongitude = localizacaoDTO.longitude().setScale(2, RoundingMode.HALF_UP);

                if (scaledStopLatitude.equals(scaledInLatitude) && scaledStopLongitude.equals(scaledInLongitude)) {
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
