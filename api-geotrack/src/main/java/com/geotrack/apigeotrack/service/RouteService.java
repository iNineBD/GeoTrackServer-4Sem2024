package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.routes.RouteDTO;
import com.geotrack.apigeotrack.dto.routes.find.ResponseFindRoutesDTO;
import com.geotrack.apigeotrack.dto.routes.find.RoutesOracleDTO;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class RouteService {

    @Autowired
    private LocationRepository locationRepository;

    @Cacheable(value = "routes", key = "#deviceId + '_' + #dateStart.toString() + '_' + #dateEnd.toString()")
   public List<ResponseFindRoutesDTO> findRoutes(Long deviceId, LocalDate dateStart, LocalDate dateEnd) {
    List<ResponseFindRoutesDTO> response = new ArrayList<>();
    List<List<RoutesOracleDTO>> routesObjects = UtilsServices.convertToRouteSQLDTO(
            locationRepository.findRouteByIdDevAndDate(deviceId, dateStart, dateEnd));

    validateRoutes(deviceId, dateStart, dateEnd, routesObjects);

    for (List<RoutesOracleDTO> route : routesObjects) {
        if (route.size() >= 3) {
            response.add(getResponseFindRoutesDTO(route));
        }
    }

    if (response.isEmpty()) {
        throw new NoSuchElementException("Não foi possível encontrar rotas para o dispositivo " + deviceId + " entre as datas " + dateStart + " e " + dateEnd);
    }

    return response;
}

private void validateRoutes(Long deviceId, LocalDate dateStart, LocalDate dateEnd, List<List<RoutesOracleDTO>> routesObjects) {
    if (routesObjects.isEmpty() || routesObjects.size() < 3) {
        throw new NoSuchElementException("Não foi possível encontrar rotas para o dispositivo " + deviceId + " entre as datas " + dateStart + " e " + dateEnd);
    }
}

    private static ResponseFindRoutesDTO getResponseFindRoutesDTO(List<RoutesOracleDTO> route) {
        ResponseFindRoutesDTO responseFindRoutesDTO = new ResponseFindRoutesDTO();
        responseFindRoutesDTO.setDateStart(route.getFirst().getDataHora().toString());
        responseFindRoutesDTO.setDateEnd(route.getLast().getDataHora().toString());
        List<RouteDTO> routesDTO = new ArrayList<>();
        for (RoutesOracleDTO routeObject : route) {
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setLatitude(routeObject.getLatitude());
            routeDTO.setLongitude(routeObject.getLongitude());
            routeDTO.setDate(routeObject.getDataHora().toString());
            routesDTO.add(routeDTO);
        }
        responseFindRoutesDTO.setCoordinates(routesDTO);
        return responseFindRoutesDTO;
    }
}
