package com.geotrack.apigeotrack.dto.routes.find;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geotrack.apigeotrack.dto.routes.RouteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseFindRoutesDTO {
    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("coordinates")
    private List<RouteDTO> coordinates;
}
