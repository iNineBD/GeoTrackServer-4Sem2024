package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.service.PopulateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Tag(name = "Populate", description = "Operações para popular o banco de dados")
@Controller
@RequestMapping("/populate")
public class PopulateController {


    @Autowired
    PopulateService populateService;

    @Operation(summary = "Popular dados de localização no banco de dados", description = "Popular o banco de dados com os dados de localização recebidos pelo cliente")
    @PostMapping()
    public ResponseEntity<String> enterDatas() throws IOException {
        populateService.populate("C:\\Users\\joaom\\Downloads\\Fatec_Locations_202409212325.json");
        return ResponseEntity.ok("Dados inseridos");
    }
}
