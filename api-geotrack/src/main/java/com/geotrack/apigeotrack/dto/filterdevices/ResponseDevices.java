package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDevices(@JsonAlias("listDevices") List<DataDevicesDTO> listDevices,
                              @JsonAlias("currentPage") int currentPage,
                              @JsonAlias("totalPages") int totalPages) {
}
