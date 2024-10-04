package com.geotrack.apigeotrack.service.utils;

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
                    ((BigDecimal) result[0]).setScale(4, RoundingMode.HALF_UP),
                    ((BigDecimal) result[1]).setScale(4, RoundingMode.HALF_UP),
                    ((Number) result[2]).intValue(),
                    (String) result[5],
                    ((Timestamp) result[3]).toLocalDateTime(),
                    ((Timestamp) result[4]).toLocalDateTime()
            );
            stopPoints.add(stopPoint);
        }
        return stopPoints;
    }

}
