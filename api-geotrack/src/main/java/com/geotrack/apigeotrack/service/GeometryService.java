package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.repositories.GeometryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeometryService {

    @Autowired
    GeometryRepository geometryRepository;
}
