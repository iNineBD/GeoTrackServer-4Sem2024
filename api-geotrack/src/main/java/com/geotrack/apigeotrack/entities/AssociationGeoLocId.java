package com.geotrack.apigeotrack.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociationGeoLocId implements Serializable{

    private Long idSession;
    private Long idDevices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationGeoLocId that = (AssociationGeoLocId) o;
        return Objects.equals(idSession, that.idSession) &&
                Objects.equals(idDevices, that.idDevices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSession, idDevices);
    }

}
