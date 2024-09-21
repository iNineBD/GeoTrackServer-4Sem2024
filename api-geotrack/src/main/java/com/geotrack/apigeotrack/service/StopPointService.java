package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StopPointService {

    @Autowired
    LocationRepository locationRepository;

    public List<LocalizacaoDTO> latLongCal(StopPointRequestDTO requestDTO) {


        LocalDateTime time = LocalDateTime.now();
        List<Location> localizations = locationRepository.listLocal(requestDTO.device(), requestDTO.startDate(), requestDTO.finalDate());
        System.out.printf("A consulta demorou: " + Duration.between(time, LocalDateTime.now()));

        if (localizations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        List<LocalizacaoDTO> stopPoints = new ArrayList<>();

        LocalizacaoDTO iteratorLocation = new LocalizacaoDTO(localizations.getFirst().getLatitude(),localizations.getFirst().getLongitude(),localizations.getFirst().getDateTime());
        for (int i = 1; i < localizations.size(); i++) {
            LocalizacaoDTO currentLocation = new LocalizacaoDTO(localizations.get(i).getLatitude(),localizations.get(i).getLongitude(),localizations.get(i).getDateTime());

            BigDecimal latitude = iteratorLocation.latitude();
            BigDecimal longitude = iteratorLocation.longitude();

            LocalizacaoDTO location = new LocalizacaoDTO(latitude, longitude,null);

            // Verifica se já existe no conjunto
            if (stopPoints.contains(location)) {

                iteratorLocation = new LocalizacaoDTO(localizations.get(i).getLatitude(),localizations.get(i).getLongitude(),localizations.get(i).getDateTime());
                continue;
            }
            Timestamp timeMore15 = Timestamp.valueOf(iteratorLocation.dataHora().toLocalDateTime().plusMinutes(15));
            Timestamp normalTime = Timestamp.valueOf(currentLocation.dataHora().toLocalDateTime());

            if (timeMore15.after(normalTime)) {
                stopPoints.add(location);
            }
        }
        return stopPoints;
    }

    public List<FeatureDTO> resquestGeoJson(List<LocalizacaoDTO> stopPoints) {

        List<FeatureDTO> feature = new ArrayList<>(stopPoints.size());

        for (LocalizacaoDTO point : stopPoints) {

            BigDecimal[] listCoordenates = {point.longitude(),point.latitude()};

            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);

            feature.add(new FeatureDTO("Feature",new PropertiesDTO(), geometry));
        }
        return feature;
    }
}
