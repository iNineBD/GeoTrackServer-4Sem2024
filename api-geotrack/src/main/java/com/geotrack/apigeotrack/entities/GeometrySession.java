package com.geotrack.apigeotrack.entities;

import com.geotrack.apigeotrack.service.utils.GeometryForms;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sessao_geometrica", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeometrySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao", nullable = false)
    private Integer idSession;

    @Column(name = "nome", length = 50)
    private String name;

    @Column(name = "status")
    private Integer status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private GeometryForms type;

    @Column(name = "longitude_centro", precision = 17, scale = 15)
    private BigDecimal longitude;

    @Column(name = "latitude_centro", precision = 17, scale = 15)
    private BigDecimal latitude;

    @Column(name = "raio", precision = 25, scale = 15)
    private BigDecimal radius;

}
