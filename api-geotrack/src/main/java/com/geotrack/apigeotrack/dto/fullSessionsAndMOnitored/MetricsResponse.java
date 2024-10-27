package com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetricsResponse(@JsonAlias("monitored") int qtdMonitored,
                              @JsonAlias("sessions") int qtdSessions) {
}
