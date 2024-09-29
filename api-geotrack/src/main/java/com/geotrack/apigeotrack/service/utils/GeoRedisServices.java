package com.geotrack.apigeotrack.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class GeoRedisServices {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void addLocation(String key, BigDecimal latitude, BigDecimal longitude, String member) {
        redisTemplate.opsForGeo().add(key, new Point(longitude.doubleValue(), latitude.doubleValue()), member);
        redisTemplate.expire(key, 120, TimeUnit.SECONDS);
    }

    public Distance calculateDistance(String key, String member1, String member2) {
        return redisTemplate.opsForGeo().distance(key, member1, member2, RedisGeoCommands.DistanceUnit.METERS);
    }

    public void removeLocation(String key, String member) {
        redisTemplate.opsForGeo().remove(key, member);
    }
}
