package com.geotrack.apigeotrack.dto.StopPoint;

import java.math.BigDecimal;
import java.util.List;

public record GeometryDTO(String type, List<Double> coordinates ) {
}
