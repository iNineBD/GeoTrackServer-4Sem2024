package com.geotrack.apigeotrack.dto.routes.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutesOracleDTO {

    private String idLocalizacao;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime dataHora;
    private Integer grupoSeq;


}
