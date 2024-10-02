package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.service.GeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geometry")
public class GeometryController {

    @Autowired
    GeometryService geometryService;
}