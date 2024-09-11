package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "senha")
    private String senha;
    @Column(name = "cpf")
    private String cpf;

    @OneToMany
    @JoinColumn(name = "id_dispositivo")
    private List<Dispositivo> dispositivos;
}
