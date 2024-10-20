package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import com.geotrack.apigeotrack.dto.stopoint.StopPointDBDTO;
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

    public static List<StopPointDBDTO> convertToStopPointDTO(List<Object[]> results) {
        List<StopPointDBDTO> stopPoints = new ArrayList<>();
        for (Object[] result : results) {
            StopPointDBDTO stopPoint = new StopPointDBDTO(
                    ((Number) result[0]).intValue(),
                    ((BigDecimal) result[1]).setScale(4, RoundingMode.HALF_UP),
                    ((BigDecimal) result[2]).setScale(4, RoundingMode.HALF_UP),
                    ((Number) result[3]).intValue(),
                    (String) result[6],
                    ((Timestamp) result[4]).toLocalDateTime(),
                    ((Timestamp) result[5]).toLocalDateTime()
            );
            stopPoints.add(stopPoint);
        }
        return stopPoints;
    }

    public static boolean checkStopPointDuplicate(StopPointDBDTO stopPointToCheck, List<LocalizacaoDTO> stopPointsInSession) {
        if (!stopPointsInSession.isEmpty()) {

            // setScale 2 is a tecnic to decrease repetition
            BigDecimal scaledInLatitude = stopPointToCheck.latitude().setScale(2, RoundingMode.HALF_UP);
            BigDecimal scaledInLongitude = stopPointToCheck.longitude().setScale(2, RoundingMode.HALF_UP);

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