package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.insertdata.RequestInsert;
import com.geotrack.apigeotrack.service.InsertDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Insert Date", description = "Operations to insert location")
public class InsertDataController {

    @Autowired
    private InsertDataService insertDataService;

    @PostMapping("/location")
    public void enterDatas(@RequestBody List<RequestInsert> requestInserts) throws Exception {
        insertDataService.insertDataService(requestInserts);
    }

}
