package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "geo_sessao", schema = "ITO1")
@NoArgsConstructor
@Getter
@Setter
public class GeometrySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao")
    private int idSession;

    @Column(name = "nome")
    private String name;

    @Column(name = "GEOMETRY", columnDefinition = "SDO_GEOMETRY")
    private Geometry geometry;

}
