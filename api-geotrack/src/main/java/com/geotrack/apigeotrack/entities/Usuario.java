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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "senha")
    private String senha;
    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "usuario")
    private List<Dispositivo> dispositivos;
}
