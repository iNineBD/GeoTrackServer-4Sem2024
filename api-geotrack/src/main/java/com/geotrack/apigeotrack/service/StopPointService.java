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

        // Check if you made the request with more than 5 users
        if(requestDTO.devices().size() > 5){
            throw new IllegalArgumentException("Limite de dispositivos excedido. Máximo de 5 por consulta");
        }
        List<StopPointResponseDTO> deviceGeoJsonList = new ArrayList<>();

        //Returns a list, ordered by device id and grouped by a time of 15 minutes, with the average of latitudes and longitudes
        List<StopPointDBDTO> listStop = UtilsServices.convertToStopPointDTO(locationRepository.findStopPointsByUsers(requestDTO.devices(), requestDTO.startDate(), requestDTO.finalDate()));

        // Checks if the list is empty
        if (listStop.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada para os dispositivos. ");
        }
        List<LocalizacaoDTO> stopPoints = new ArrayList<>();

        // Get the first device id from the list
        Integer idPrevious = listStop.get(0).idDev();

        // Create an iterator
        int i = 0;

        // It will go through each line of the listStop
        for (StopPointDBDTO stopPointDBDTO : listStop) {

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
                    List<FeatureDTO> feature = resquestGeoJson(stopPoints);
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


    public LocalizacaoDTO toExecStopPoint(StopPointDBDTO in, List<LocalizacaoDTO> stopPoints) {

        // validates if the coordinate has already been inserted in the list
        if (UtilsServices.checkStopPointDuplicate(in, stopPoints)) return null;

        // create a unique id to indentify register in cache
        String idRegister = in.startDate().toString() + in.endDate().toString() + in.latitude() + in.longitude();

        // add avg lat and long in cache
        geoRedisService.addLocation(idRegister, in.latitude(), in.longitude(), "pontoMedio");

        // db return a list of lat and long. Here we separate this
        String[] coordinates= in.latLongList().split("\\|");
        for(int i = 0 ; i < coordinates.length ; i++) {

            // bd return a "map" to lat e long. Here we separate this
            String[] latLongArray = coordinates[i].split(";");
            BigDecimal latitude = new BigDecimal(latLongArray[0].replace(",", "."));
            BigDecimal longitude = new BigDecimal(latLongArray[1].replace(",", "."));

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

        return new LocalizacaoDTO(in.latitude(), in.longitude());
    }



    public List<FeatureDTO> resquestGeoJson(List<LocalizacaoDTO> stopPoints) {

        // Creates a list of feature objects
        List<FeatureDTO> feature = new ArrayList<>(stopPoints.size());

        // It will pass through all the stopping points
        for (LocalizacaoDTO point : stopPoints) {
            // Creates a list of coordinates with latitudes and longitudes
            BigDecimal[] listCoordenates = {point.latitude(),point.longitude()};

            // Creates the GeometryDTO object with latitudes and longitudes
            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);

            // Add the object to the list
            feature.add(new FeatureDTO("Feature",new PropertiesDTO(), geometry));
        }
        return feature;
    }
}
