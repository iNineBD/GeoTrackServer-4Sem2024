package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.filterdevices.DataDevicesDTO;
import com.geotrack.apigeotrack.dto.filterdevices.RequestDevice;
import com.geotrack.apigeotrack.dto.filterdevices.ResponseDevices;
import com.geotrack.apigeotrack.dto.filterusers.DataUsersDTO;
import com.geotrack.apigeotrack.dto.filterusers.RequestUser;
import com.geotrack.apigeotrack.dto.filterusers.ResponseUsers;
import com.geotrack.apigeotrack.service.FiltersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Tag(name = "Filters",description = "Operações para retornar os usuários e dispositivos")
@RestController
@RequestMapping("/filters")
public class FiltersController {

    @Autowired
    FiltersService filtersService;

    @Operation(summary = "Retorna todos os usuários", description = "Retorna uma lista de usuários paginadas com o número de 5 elementos por página")
    @GetMapping("/users")
    public ResponseEntity<ResponseUsers> filterUsers(@RequestParam int page) throws NoSuchElementException {
        RequestUser request = new RequestUser(page);
        ResponseUsers response = filtersService.listUsers(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Retorna todos os dispositivos do usuário", description = "Retorna uma lista de dispositivos do usuário paginadas com o número de 5 elementos por página")
    @GetMapping("/devices")
    public ResponseEntity<ResponseDevices> filterDevices(@RequestParam int idUser, @RequestParam int page) throws NoSuchElementException {
        RequestDevice req = new RequestDevice(idUser, page);
        ResponseDevices response = filtersService.listDevices(req);
        return ResponseEntity.ok().body(response);
    }
}
