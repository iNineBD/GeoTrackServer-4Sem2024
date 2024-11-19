package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "localizacao2", schema = "ITO1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_localizacao", nullable = false, length = 38)
    private String idLocation;

    @Column(name = "loc_geo", columnDefinition = "SDO_GEOMETRY")
    private Geometry geometry;

    @Column(name = "data_hora")
    private Timestamp dateTime;

    @Column(name = "data_referencia")
    private LocalDate dateReferences;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Devices devices;

}