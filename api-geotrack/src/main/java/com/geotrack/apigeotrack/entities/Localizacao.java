package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "localizacao", schema = "ITO1")
@NoArgsConstructor
@Getter
@Setter
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacao")
    private String idLocalizacao;

    @Column(name = "longitude")
    private BigDecimal longitude;

    public Localizacao(String idLocalizacao, BigDecimal longitude, BigDecimal latitude, LocalDateTime dataHora, Dispositivo dispositivo) {
        this.idLocalizacao = idLocalizacao;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dataHora = Timestamp.valueOf(dataHora);
        this.dataReferencia = LocalDate.of(dataHora.getYear(), dataHora.getMonthValue(), dataHora.getDayOfMonth());
        this.dispositivo = dispositivo;
    }

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "data_hora")
    private Timestamp dataHora;

    @Column(name = "data_referencia")
    private LocalDate dataReferencia;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Dispositivo dispositivo;
}
