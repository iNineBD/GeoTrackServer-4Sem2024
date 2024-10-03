package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "sessao", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeometryZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id_sessao")
    private Integer idSession;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "geometryZone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GeometryCoordinates> coordinates;
}
