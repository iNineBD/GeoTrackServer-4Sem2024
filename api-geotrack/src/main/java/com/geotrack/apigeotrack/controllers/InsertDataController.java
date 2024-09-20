package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.insertData.InsertDataDTO;
import com.geotrack.apigeotrack.dto.insertData.RequestInsert;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.exceptions.GlobalExceptionHandler;
import com.geotrack.apigeotrack.service.InsertDataService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/inserir-dados")
    public ResponseEntity<String> inserirDados(@RequestBody List<RequestInsert> requestInserts) throws Exception {
        insertDataService.insertDataService(requestInserts);
        return ResponseEntity.ok("Dados inseridos com sucesso!");
    }
}
