package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.service.PopulateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/populate")
public class PopulateController {


    @Autowired
    PopulateService populateService;

    @PostMapping()
    public ResponseEntity<String> enterDatas() throws IOException {
        populateService.populate("C:\\Users\\joaom\\Downloads\\Fatec_Locations_202409212325.json");
        return ResponseEntity.ok("Dados inseridos");
    }
}
