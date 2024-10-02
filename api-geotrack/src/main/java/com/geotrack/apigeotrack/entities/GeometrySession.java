package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "geo_sessao", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeometrySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id_sessao")
    private int idSession;

    @Column(name = "nome")
    private String name;

    @Type(type = "org.hibernate.spatial.GeometryType")
    @Column(name = "GEOMETRY", columnDefinition = "SDO_GEOMETRY")
    private Geometry geometry;

}
