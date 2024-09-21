package com.geotrack.apigeotrack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "localizacao", schema = "ITO1")
@NoArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_localizacao")
    private String idLocation;

    @Column(name = "longitude")
    private BigDecimal longitude;

    public Location(String idLocation, BigDecimal longitude, BigDecimal latitude, LocalDateTime dateTime, Devices devices) {
        this.idLocation = idLocation;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateTime = Timestamp.valueOf(dateTime);
        this.dateReferences = LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
        this.devices = devices;
    }

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "data_hora")
    private Timestamp dateTime;

    @Column(name = "data_referencia")
    private LocalDate dateReferences;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Devices devices;
}
