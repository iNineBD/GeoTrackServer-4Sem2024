package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Page;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDevices(@JsonAlias("listDevices") Page<DataDevicesDTO> listDevices) {
}
