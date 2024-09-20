package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
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
    LocalizacaoRepository localizacaoRepository;

    public List<LocalizacaoDTO> latLongCal(StopPointRequestDTO requestDTO) {


        LocalDateTime iha = LocalDateTime.now();
        List<Localizacao> localizations = localizacaoRepository.listLocal(requestDTO.device(), requestDTO.dataInicio(), requestDTO.dataFim());
        System.out.printf("A consulta demorou: " + Duration.between(iha, LocalDateTime.now()));

        if (localizations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma Localização encontrada");
        }

        List<LocalizacaoDTO> pontosParada = new ArrayList<>();

        LocalizacaoDTO iteratorLocation = new LocalizacaoDTO(localizations.getFirst().getLatitude(),localizations.getFirst().getLongitude(),localizations.getFirst().getDataHora());
        for (int i = 1; i < localizations.size(); i++) {
            LocalizacaoDTO currentLocation = new LocalizacaoDTO(localizations.get(i).getLatitude(),localizations.get(i).getLongitude(),localizations.get(i).getDataHora());

            BigDecimal latitude = iteratorLocation.latitude();
            BigDecimal longitude = iteratorLocation.longitude();

            LocalizacaoDTO local = new LocalizacaoDTO(latitude, longitude,null);

            // Verifica se já existe no conjunto
            if (pontosParada.contains(local)) {

                iteratorLocation = new LocalizacaoDTO(localizations.get(i).getLatitude(),localizations.get(i).getLongitude(),localizations.get(i).getDataHora());
                continue;
            }
            Timestamp tempo15 = Timestamp.valueOf(iteratorLocation.dataHora().toLocalDateTime().plusMinutes(15));
            Timestamp tempoNormal = Timestamp.valueOf(currentLocation.dataHora().toLocalDateTime());

            if (tempo15.after(tempoNormal)) {
                pontosParada.add(local);
            }
        }
        return pontosParada;
    }

    public List<FeatureDTO> resquestGeoJson(List<LocalizacaoDTO> pontosParada) {

        List<FeatureDTO> feature = new ArrayList<>(pontosParada.size());

        for (LocalizacaoDTO ponto : pontosParada) {

            BigDecimal[] listCoordenates = {ponto.longitude(),ponto.latitude()};

            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);

            feature.add(new FeatureDTO("Feature",new PropertiesDTO(), geometry));
        }
        return feature;
    }
}
