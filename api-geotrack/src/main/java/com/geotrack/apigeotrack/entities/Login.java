package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "login", schema = "ito1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Login implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 200)
    private String name;

    @Column(name = "senha", length = 200)
    private String password;

    @Column(name = "email", length = 200, unique = true)
    private String email;

    // method UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a empty list because we are not using roles
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // returning true because we are not controlling account expiration
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // returning true because we are not controlling account lock
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // returning true because we are not controlling credentials expiration
        return true;
    }

    @Override
    public boolean isEnabled() {
        // returning true because we are not controlling account enablement
        return true;
    }

}
