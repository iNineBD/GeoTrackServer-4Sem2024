package com.geotrack.apigeotrack.dto.insertdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Schema(name = "InsertDataDTO", description = "DTO para inserção de dados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertDataDTO {
    @Schema(description = "ID do ponto", example = "1")
    private int id;
    @Schema(description = "Data e hora do ponto", example = "2021-09-01 12:00:00")
    private Timestamp dateTime;
    @Schema(description = "Latitude do ponto", example = "-23.563")
    private BigDecimal latitude;
    @Schema(description = "Longitude do ponto", example = "-46.654")
    private BigDecimal longitude;
    @Schema(description = "ID do cliente", example = "1")
    private String name;
    @Schema(description = "ID do cliente", example = "1")
    private int idCustomer;
}
