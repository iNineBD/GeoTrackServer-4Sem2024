package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.insertdata.RequestInsertGeo;
import com.geotrack.apigeotrack.service.InsertDataGeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "API para inserção de dados", description = "Operações para inserir dados")
@CrossOrigin(origins = "*")
public class InsertDataGeoController {

    @Autowired
    InsertDataGeoService insertDataGeoService;

    @Operation(summary = "Insere dados de localização no banco de dados", description = "API que recebe os dados de localização e salva no banco de dados em até 200ms")
    @PostMapping("/locationUser")
    public void enterDatas(@RequestBody List<RequestInsertGeo> requestInsertGeo) throws Exception {
        insertDataGeoService.insertDataService(requestInsertGeo);
    }

}
