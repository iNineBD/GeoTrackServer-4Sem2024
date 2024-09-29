package com.geotrack.apigeotrack.service.utils;

import java.util.UUID;

public class HashGeneratorToRedis {

    public static String generateHash() {
        return UUID.randomUUID().toString();
    }
}
