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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacao")
    private int idLocalizacao;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "data_hora")
    private Timestamp dataHora;

    @Column(name = "id_base_cliente")
    private String idBaseCliente;

    @ManyToOne
    @JoinColumn(name = "fk_id_dispositivo", nullable = false)
    private Dispositivo dispositivo;
}
