package com.geotrack.apigeotrack.dto.insertData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
