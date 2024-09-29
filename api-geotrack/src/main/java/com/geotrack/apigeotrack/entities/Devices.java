package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dispositivo", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Long idDevices;

    @Column(name = "nome")
    private String name;

    @Column(name = "codigo")
    private String code;

    @Column(name = "tipo")
    private String type;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

}
