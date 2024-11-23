package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Geo_localizacao", schema = "ITO1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_localizacao", nullable = false, length = 38)
    private String idLocation;

    @Column(name = "loc_geo", columnDefinition = "SDO_GEOMETRY")
    private Point geometry;

    @Column(name = "data_hora")
    private LocalDateTime dateTime;

    @Column(name = "data_referencia")
    private LocalDate dateReferences;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Devices devices;

}