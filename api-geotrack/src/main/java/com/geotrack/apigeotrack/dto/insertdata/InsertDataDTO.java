package com.geotrack.apigeotrack.dto.insertdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertDataDTO {
    private int id;
    private Timestamp dataHora;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String nome;
    private int idCliente;
}
