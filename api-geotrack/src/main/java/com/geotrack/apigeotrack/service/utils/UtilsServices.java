package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import com.geotrack.apigeotrack.dto.stopoint.StopPointDBDTO;
import com.geotrack.apigeotrack.dto.routes.find.RoutesOracleDTO;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class UtilsServices {

    @Autowired
    LocationRepository locationRepository;

//    public static List<StopPointDBDTO> convertToStopPointDTO(List<Object[]> results) {
//        List<StopPointDBDTO> stopPoints = new ArrayList<>();
//        for (Object[] result : results) {
//            StopPointDBDTO stopPoint = new StopPointDBDTO(
//                    ((Number) result[0]).intValue(),
//                    ((BigDecimal) result[1]).setScale(4, RoundingMode.HALF_UP),
//                    ((BigDecimal) result[2]).setScale(4, RoundingMode.HALF_UP),
//                    ((BigDecimal) result[3]).setScale(4, RoundingMode.HALF_UP),
//                    ((BigDecimal) result[4]).setScale(4, RoundingMode.HALF_UP),
//                    ((Timestamp) result[5]).toLocalDateTime(),
//                    ((Number) result[6]).intValue()
//            );
//            stopPoints.add(stopPoint);
//        }
//        return stopPoints;
//    }

    public static List<List<RoutesOracleDTO>> convertToRouteSQLDTO(List<Object[]> results) {
        List<List<RoutesOracleDTO>> returned = new ArrayList<>();

        List<RoutesOracleDTO> routes = new ArrayList<>();
        int atual = 0;
        for (Object[] result : results) {

            if (atual != ((Number) result[4]).intValue()) {
                atual = ((Number) result[4]).intValue();
                returned.add(routes);
                routes = new ArrayList<>();
            }

            RoutesOracleDTO stopPoint = new RoutesOracleDTO(
                    (String) result[0],
                    ((BigDecimal) result[1]).setScale(8, RoundingMode.HALF_UP),
                    ((BigDecimal) result[2]).setScale(8, RoundingMode.HALF_UP),
                    ((Timestamp) result[3]).toLocalDateTime(),
                    ((Number) result[4]).intValue()
            );
            routes.add(stopPoint);
        }
        if (!routes.isEmpty()) {
            returned.add(routes);
        }
        return returned;
    }

    public static boolean checkStopPointDuplicate(StopPointDBDTO stopPointToCheck, List<LocalizacaoDTO> stopPointsInSession) {
        if (!stopPointsInSession.isEmpty()) {

            // setScale 2 is a tecnic to decrease repetition
            BigDecimal scaledInLatitude = stopPointToCheck.avgLatitude().setScale(2, RoundingMode.HALF_UP);
            BigDecimal scaledInLongitude = stopPointToCheck.avgLongitude().setScale(2, RoundingMode.HALF_UP);

            for (LocalizacaoDTO localizacaoDTO : stopPointsInSession) {
                BigDecimal scaledStopLatitude = localizacaoDTO.latitude().setScale(2, RoundingMode.HALF_UP);
                BigDecimal scaledStopLongitude = localizacaoDTO.longitude().setScale(2, RoundingMode.HALF_UP);


                // check lat equals lat and long equals long
                if (scaledStopLatitude.equals(scaledInLatitude) && scaledStopLongitude.equals(scaledInLongitude)) {
                    return true;
                }
            }
        }
        return false;
    }
}