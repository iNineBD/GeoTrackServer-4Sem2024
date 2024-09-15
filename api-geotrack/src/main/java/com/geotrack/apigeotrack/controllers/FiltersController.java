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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Tag(name = "Filters",description = "Operações para retornar os usuários e dispositivos")
@RestController
@RequestMapping("/filters")
public class FiltersController {

    @Autowired
    FiltersService filtersService;

    @Operation(summary = "Retorna todos os usuários", description = "Retorna uma lista de usuários paginadas com o número de 5 elementos por página")
    @PostMapping("/users")
    public ResponseEntity<ResponseUsers> filterUsers(@RequestBody RequestUser request) throws NoSuchElementException {
        Page<DataUsersDTO> usersPage =  filtersService.listUsers(request);
        ResponseUsers response = filtersService.listUsersResponse(usersPage);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Retorna todos os dispositivos do usuário", description = "Retorna uma lista de dispositivos do usuário paginadas com o número de 5 elementos por página")
    @PostMapping("/devices")
    public ResponseEntity<ResponseDevices> filterDevices(@RequestBody RequestDevice request) throws NoSuchElementException {
        Page<DataDevicesDTO> devicesPage = filtersService.listDevices(request);
        ResponseDevices response = filtersService.listDevicesResponse(devicesPage);
        return ResponseEntity.ok().body(response);
    }
}
