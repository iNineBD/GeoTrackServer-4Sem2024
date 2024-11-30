package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "assoc_geoloc_sessao", schema = "ITO1")
public class AssociationGeoLoc {

    @EmbeddedId
    private AssociationGeoLocId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSession")
    @JoinColumn(name = "id_sessao", nullable = false, referencedColumnName = "id_sessao", foreignKey = @ForeignKey(name = "fk_sessao_geometrica"))
    private GeometrySession idSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idDevices")
    @JoinColumn(name = "id_dispositivo", nullable = false, referencedColumnName = "id_dispositivo", foreignKey = @ForeignKey(name = "fk_geo_localizacao"))
    private Devices idDevices;
}
