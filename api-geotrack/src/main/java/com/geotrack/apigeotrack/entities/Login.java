package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "login", schema = "ito1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome",length = 200)
    private String name;

    @Column(name = "senha",length = 200)
    private String password;

    @Column(name = "email",length = 200, unique = true)
    private String email;

}
