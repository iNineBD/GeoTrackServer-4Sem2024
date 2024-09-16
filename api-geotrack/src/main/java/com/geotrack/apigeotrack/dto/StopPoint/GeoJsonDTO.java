package com.geotrack.apigeotrack.dto.StopPoint;

import java.util.List;

public record GeoJsonDTO(String type, List<FeatureDTO> features) {
}
