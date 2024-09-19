package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StopPointService {

    @Autowired
    LocalizacaoRepository localizacaoRepository;

    public List<LocalizacaoDTO> latLongCal(StopPointRequestDTO requestDTO) {


        LocalDateTime iha = LocalDateTime.now();
        List<Localizacao> localizations = localizacaoRepository.listLocal(
                requestDTO.device(), requestDTO.dataInicio(), requestDTO.dataFim());
        System.out.printf("A consulta demorou: " + Duration.between(iha, LocalDateTime.now()));
        if (localizations.isEmpty()) {
            throw new RuntimeException("Nenhuma Localização encontrada");
        }

        List<LocalizacaoDTO> pontosParada = new ArrayList<>();
        int iterator = 0;

        LocalizacaoDTO iteratorLocation = new LocalizacaoDTO(localizations.get(iterator).getLatitude(),localizations.get(iterator).getLongitude(),localizations.get(iterator).getDataHora());
        for (int i = 1; i < localizations.size(); i++) {
            LocalizacaoDTO currentLocation = new LocalizacaoDTO(localizations.get(i).getLatitude(),localizations.get(i).getLongitude(),localizations.get(i).getDataHora());

            BigDecimal latitude = iteratorLocation.latitude();
            BigDecimal longitude = iteratorLocation.longitude();
            LocalizacaoDTO local = new LocalizacaoDTO(latitude, longitude,null);

            // Verifica se já existe no conjunto
            if (pontosParada.contains(local)) {
                iterator = i;
                iteratorLocation = new LocalizacaoDTO(localizations.get(iterator).getLatitude(),localizations.get(iterator).getLongitude(),localizations.get(iterator).getDataHora());
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


    public List<FeatureDTO> resquestGeoJson (List<LocalizacaoDTO> pontosParada){

        List<FeatureDTO> feature = new ArrayList<>();

        for (LocalizacaoDTO ponto : pontosParada) {

            BigDecimal[] listCoordenates = new BigDecimal[2];

            BigDecimal latitude = ponto.latitude();
            BigDecimal longitude = ponto.longitude();

            listCoordenates[0] = latitude;
            listCoordenates[1] = longitude;

            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);
            PropertiesDTO properties = new PropertiesDTO();
            feature.add(new FeatureDTO("Feature", properties, geometry));
        }

        return feature;
    }
}
