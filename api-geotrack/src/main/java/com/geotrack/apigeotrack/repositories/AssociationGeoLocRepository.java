package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.AssociationGeoLoc;
import com.geotrack.apigeotrack.entities.AssociationGeoLocId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssociationGeoLocRepository extends JpaRepository<AssociationGeoLoc, AssociationGeoLocId> {

    @Query("select distinct d.idDevices.idDevices from AssociationGeoLoc d where d.idSession.idSession = :idSession")
    List<Long> listDevices(Integer idSession);


    @Query("delete from AssociationGeoLoc d where d.idSession.idSession = :idSession")
    void deleteAssoc(Integer idSession);

}
