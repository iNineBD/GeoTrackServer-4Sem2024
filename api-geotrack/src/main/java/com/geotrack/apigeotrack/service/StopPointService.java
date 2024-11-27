package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Devices;
import com.geotrack.apigeotrack.repositories.DevicesRepository;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.GeoRedisServices;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Operation(summary = "Retorna os pontos de parada dos dispositivos", description = "Retorna os pontos de parada dos dispositivos")
    @Cacheable(value = "stoppingPoints", key = "#requestDTO")
    public List<StopPointResponseDTO> findStopPointByDeviceAndData(StopPointRequestDTO requestDTO) {

        // Check if you made the request with more than 5 users
        if(requestDTO.devices().size() > 5){
            throw new IllegalArgumentException("Limite de dispositivos excedido. Máximo de 5 por consulta");
        }
        List<StopPointResponseDTO> deviceGeoJsonList = new ArrayList<>();
        List<Object[]> listStop = locationRepository.findStopPointsByUsersWithoutSession(requestDTO.devices(), requestDTO.startDate(), requestDTO.finalDate());
        //Returns a list, ordered by device id and grouped by a time of 15 minutes, with the average of latitudes and longitudes

        // Checks if the list is empty
        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada para os dispositivos. ");
        }
        List<LocalizacaoDTO> stopPoints = new ArrayList<>();

        // Get the first device id from the list
        Integer idPrevious = listStop.getFirst()[0] != null ? ((BigDecimal) listStop.getFirst()[0]).intValue() : null;

        // Create an iterator
        int i = 0;

        // It will go through each line of the listStop
        for (Object[] temp : listStop) {

            StopPointDBDTO stopPointDBDTO = new StopPointDBDTO(
                    ((BigDecimal) temp[0]).intValue(),
                    (BigDecimal) temp[1],
                    (BigDecimal) temp[2],
                    (BigDecimal) temp[5],
                    (BigDecimal) temp[6],
                    ((Timestamp) temp[3]).toLocalDateTime(),
                    ((BigDecimal) temp[5]).intValue()
            );

            //Gets the object's current device id number
            Integer idCurrent = stopPointDBDTO.idDev();

            // When the previous id is different from the current one, it will save that user's geojson list or when it is the last device on the list
            if(!idCurrent.equals(idPrevious) || i == listStop.size() - 1){

                // Checks whether the stopPoints list has any stop points
                if (!stopPoints.isEmpty()) {
                    // Search for device based on ID
                    Optional<Devices> device = devicesRepository.findById(idPrevious);
                    // Gets the name of the user to whom that device belongs
                    String userName = device.get().getUser().getName().toUpperCase();
                    // Get the device name
                    String nameDevice = device.get().getCode().toUpperCase();

                    // Create the feature list
                    List<FeatureDTO> feature = requestGeoJson(stopPoints);
                    // Create the geojson object
                    GeoJsonDTO geoJson = new GeoJsonDTO("FeatureCollection",feature);
                    // Add that user's stopping points to the list
                    deviceGeoJsonList.add(new StopPointResponseDTO(userName,nameDevice, geoJson));
                }
                // Add the current id number to the previous id variable
                idPrevious = idCurrent;
                // Clears the list of stopping points
                stopPoints.clear();
            }
            // Calls toExecStopPoint to get stop points
            LocalizacaoDTO point = toExecStopPoint(stopPointDBDTO, stopPoints);
            // Checks if the point object is not null
            if (point != null) {
                stopPoints.add(point);
            }
            i++;
        }

        return deviceGeoJsonList;
    }


    @Operation(summary = "Logica de Comparação de Pontos de Parada", description = "Logica de Comparação de Pontos de Parada dos dispositivos de acordo com distancia entre os pontos")
    public LocalizacaoDTO toExecStopPoint(StopPointDBDTO in, List<LocalizacaoDTO> stopPoints) {

        // validates if the coordinate has already been inserted in the list
        if (UtilsServices.checkStopPointDuplicate(in, stopPoints)) return null;

        // create a unique id to indentify register in cache
        String idRegister = in.dataHora().toString() + in.avgLatitude() + in.avgLongitude();



        // add avg lat and long in cache
        geoRedisService.addLocation(idRegister, in.avgLatitude(), in.avgLongitude(), "pontoMedio");

        // db return a list of lat and long. Here we separate this
        int coordinates = in.grupoLocalizacao();
        for(int i = 0 ; i < coordinates ; i++) {

            // bd return a "map" to lat e long. Here we separate this
            BigDecimal latitude = in.latitude();
            BigDecimal longitude = in.longitude();

            // add px in cache
            geoRedisService.addLocation(idRegister, latitude, longitude, "p"+i);

            // calcule the distance between "pontoMedio" and px
            Distance distance = geoRedisService.calculateDistance(idRegister, "pontoMedio", "p"+i);

            // remove px from cache
            geoRedisService.removeLocation(idRegister, "p"+i);

            //verify if the distance between px and pontoMedio is greater than 8 meters
            if (distance.getValue() > 8){
                return null;
            }
        }
        // remove pontoMedio from cache
        geoRedisService.removeLocation(idRegister, "pontoMedio");

        String datestart = in.dataHora().toString();


        if (!stopPoints.isEmpty() && stopPoints.get(0) != null) {
            datestart = stopPoints.getFirst().startDate();
        }

        return new LocalizacaoDTO(in.avgLatitude(), in.avgLongitude(), datestart, in.dataHora().toString());
    }


    @Operation(summary = "Criação de GeoJson", description = "Criação de GeoJson para os pontos de parada")
    public List<FeatureDTO> requestGeoJson(List<LocalizacaoDTO> stopPoints) {

        // Creates a list of feature objects
        List<FeatureDTO> feature = new ArrayList<>(stopPoints.size());

        // It will pass through all the stopping points
        for (LocalizacaoDTO point : stopPoints) {
            // Creates a list of coordinates with latitudes and longitudes
            BigDecimal[] listCoordenates = {point.longitude(),point.latitude()};

            String startDate = stopPoints.get(0).startDate();

            // Creates the GeometryDTO object with latitudes and longitudes
            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates,startDate, point.endDate());

            // Add the object to the list
            feature.add(new FeatureDTO("Feature",new PropertiesDTO(), geometry));
        }
        return feature;
    }
}
