package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int longitude;
    @Column(name = "latitude")
    private int latitude;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "fk_id_dispositivo", nullable = false)
    private Dispositivo dispositivo;
}
