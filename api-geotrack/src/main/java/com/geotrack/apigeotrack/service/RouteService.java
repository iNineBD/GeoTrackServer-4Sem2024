package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.routes.RouteDTO;
import com.geotrack.apigeotrack.dto.routes.RouteSQLDTO;
import com.geotrack.apigeotrack.dto.routes.find.ResponseFindRoutesDTO;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import com.geotrack.apigeotrack.service.utils.UtilsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RouteService {

    @Autowired
    private LocationRepository locationRepository;

    public List<ResponseFindRoutesDTO> findRoutes(Long deviceId, LocalDate date) {

        List<Object[]> routesObjects = locationRepository.findRouteByIdDevAndDate(deviceId, date);
        if (routesObjects == null || routesObjects.isEmpty()) {
            throw new NoSuchElementException("Não foi possível encontrar rotas para o dispositivo " + deviceId + " na data " + date);
        }

        List<RouteSQLDTO> routes = UtilsServices.convertToRouteSQLDTO(routesObjects);
        if (routes.isEmpty()) {
            throw new NoSuchElementException("Não foi possível encontrar rotas para o dispositivo " + deviceId + " na data " + date);
        }


        List<ResponseFindRoutesDTO> response = new ArrayList<>();
        for (RouteSQLDTO route : routes) {
            // here we verify if the route is valid
            // if the route is valid, we add it to the response

            ResponseFindRoutesDTO responseFindRoutesDTO = new ResponseFindRoutesDTO();
            responseFindRoutesDTO.setDateStart(route.getStartDate());
            responseFindRoutesDTO.setDateEnd(route.getStopDate());

            List<RouteDTO> routesDTO = new ArrayList<>();
            String[] coordinatesString = route.getCoordinates().split("->");
            for (String coordinate : coordinatesString) {
                String[] latLongDate = coordinate.split("\\|");
                String[] latLong = latLongDate[0].split(";");
                BigDecimal lon = new BigDecimal(latLong[1].replace(",", "."));
                BigDecimal lat = new BigDecimal(latLong[0].replace(",", "."));

                routesDTO.add(new RouteDTO(latLongDate[1],lat, lon));
            }

            responseFindRoutesDTO.setRoutes(routesDTO);
            response.add(responseFindRoutesDTO);

        }
        return response;
    }
}
