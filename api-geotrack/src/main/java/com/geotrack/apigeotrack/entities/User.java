package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario", schema = "ITO1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUser;
    @Column(name = "nome")
    private String name;
    @Column(name = "cargo")
    private String position;
    @Column(name = "senha")
    private String password;
    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "user")
    private List<Devices> devices;
}
