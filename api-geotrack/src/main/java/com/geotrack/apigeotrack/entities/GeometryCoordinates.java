package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "coordenadas", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeometryCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coord", nullable = false)
    private Integer idReferencia;

    @Column(name = "longitude",precision = 9,scale = 6)
    private BigDecimal longitude;

    @Column(name = "latitude",precision = 9,scale = 6)
    private BigDecimal latitude;
}
