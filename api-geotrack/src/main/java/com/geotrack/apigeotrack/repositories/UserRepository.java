package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u order by u.name asc ")
    Optional<Page<User>> listUser(PageRequest page);

    Optional<User> findById(Integer id);
}
