package com.geotrack.apigeotrack.entities;

import com.geotrack.apigeotrack.service.utils.GeometryForms;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sessao", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeometryZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao", nullable = false)
    private Integer idSession;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "status")
    private Integer status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private GeometryForms type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sessao")
    private List<GeometryCoordinates> coordinates;
}
