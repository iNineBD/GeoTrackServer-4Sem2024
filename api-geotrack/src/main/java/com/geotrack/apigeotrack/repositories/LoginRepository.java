package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {

    // method to find a user by email
    UserDetails findByEmail(String email);

}
