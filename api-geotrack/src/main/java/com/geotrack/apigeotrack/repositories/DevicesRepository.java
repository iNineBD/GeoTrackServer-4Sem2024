package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Devices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DevicesRepository extends JpaRepository<Devices, Integer> {

    @Query("select d from Devices d where d.user.idUser = :idUser order by d.name asc")
    Optional<Page<Devices>> listDevices(int idUser, PageRequest pageable);

    Optional<Devices> findByIdDevices(Long id);

    @Query("select count(d) from Devices d where d.status = 1")
    Integer qtdMonitoredAssets();

    @Query("select distinct u.name from Devices d join User u on u.idUser = d.user.idUser where d.idDevices in :idDevices order by u.name asc")
    List<String> listUsersInSession (List<Long> idDevices);

}
