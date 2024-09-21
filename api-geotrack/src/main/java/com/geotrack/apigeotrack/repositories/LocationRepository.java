package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.dateTime BETWEEN :startDate AND :finalDate AND l.devices.idDevices" +
            "= :id ORDER BY l.dateTime ASC")
    List<Location> findByDispositivoIdDispositivo(Long id, Timestamp startDate, Timestamp finalDate);

    @Query("select l from Location l where l.devices.idDevices = :idDev and (l.latitude, l.longitude) in" +
            "(select l.latitude,l.longitude from Location l where l.devices.idDevices = :idDev group by l.latitude,l.longitude having count (l) > 1)" +
            "and l.dateTime between :startDate AND :finalDate order by l.latitude,l.longitude, l.dateTime asc")
    List<Location> listLocal (Long idDev, Timestamp startDate, Timestamp finalDate);
}
