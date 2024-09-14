package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.filterdevices.ResponseDevices;
import com.geotrack.apigeotrack.dto.filterusers.RequestDevice;
import com.geotrack.apigeotrack.dto.filterusers.ResponseUsers;
import com.geotrack.apigeotrack.service.FiltersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/devices")
    public ResponseEntity<ResponseDevices> filterDevices(@RequestBody RequestDevice request) {
        ResponseDevices devices = new ResponseDevices(filtersService.listDevices(request));
        return ResponseEntity.ok().body(devices);
    }
}
