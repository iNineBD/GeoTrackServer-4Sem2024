package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.insertData.RequestInsert;
import com.geotrack.apigeotrack.service.InsertDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InsertDataController {

    @Autowired
    private InsertDataService insertDataService;

    @PostMapping("/location")
    public void inserirDados(@RequestBody List<RequestInsert> requestInserts) throws Exception {
        insertDataService.insertDataService(requestInserts);
    }

    //todo: cadastrar user e dispositivo
}
