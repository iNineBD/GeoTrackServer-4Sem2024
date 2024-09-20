package com.geotrack.apigeotrack.dto.insertData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertDataDTO {
private int id;
private LocalDateTime dataHora;
private BigDecimal latitude;
private BigDecimal longitude;
private String nome;
private int idCliente;

    public InsertDataDTO(Usuario usuario, Localizacao localizacao, Dispositivo dispositivo) {
this.id = localizacao.getIdLocalizacao();
this.dataHora = localizacao.getDataHora();
this.latitude = localizacao.getLatitude();
this.longitude = localizacao.getLongitude();
this.nome = usuario.getNome();
this.idCliente = dispositivo.getUsuario().getIdUsuario();
}
}
