package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.filterdevices.ResponseUsers;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.service.FiltersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filters")
public class FiltersController {


    @Autowired
    FiltersService filtersService;

    @GetMapping("/users")
    public ResponseEntity<ResponseUsers> filterUsers() {
        ResponseUsers users = new ResponseUsers(filtersService.listUsers());
        return ResponseEntity.ok().body(users);
    }
}
