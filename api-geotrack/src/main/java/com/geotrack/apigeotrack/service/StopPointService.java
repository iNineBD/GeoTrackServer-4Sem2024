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
import java.util.*;

@Service
public class StopPointService {

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    LocalizacaoRepository localizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Dispositivo> userDeviceNames(StopPointRequestDTO requestDTO) {

        Optional<Dispositivo> userDevice = dispositivoRepository.findByUsuarioIdUsuario(
                requestDTO.user());

        if (userDevice.isEmpty()) {
            throw new RuntimeException("Nenhum dispositivo encontrado");
        }

        return userDevice;
    }

    public List<FeatureDTO> latLongCal(StopPointRequestDTO requestDTO) {

        List<Localizacao> localizations = localizacaoRepository.findByDispositivoIdDispositivo(
                requestDTO.device(), requestDTO.dataInicio(), requestDTO.dataFim());

        if (localizations.isEmpty()) {
            throw new RuntimeException("Nenhuma Localização encontrada");
        }

        Set<LocalizacaoDTO> pontosParada = new HashSet<>();


        for (int i = 0; i < localizations.size() - 1; i++) {

            if ((localizations.get(i).getLatitude().equals(localizations.get(i + 1).getLatitude())
                    && (localizations.get(i).getLongitude().equals(localizations.get(i + 1).getLongitude())))) {

                Timestamp tempo15 =
                        Timestamp.valueOf(localizations.get(i).getDataHora().toLocalDateTime().plusMinutes(15));
                Timestamp tempoNormal = Timestamp.valueOf(localizations.get(i + 1).getDataHora().toLocalDateTime());

                if (tempo15.after(tempoNormal)) {
                    BigDecimal latitude = localizations.get(i).getLatitude();
                    BigDecimal longitude = localizations.get(i).getLongitude();
                    LocalizacaoDTO local = new LocalizacaoDTO(latitude, longitude);
                    pontosParada.add(local);
                }
            }
        }

        List<FeatureDTO> feature = new ArrayList<>();

        for (LocalizacaoDTO ponto : pontosParada) {

            Set<BigDecimal> listCoordenates = new HashSet<>();
            BigDecimal latitude = ponto.latitude();
            BigDecimal longitude = ponto.longitude();

            listCoordenates.add(latitude);
            listCoordenates.add(longitude);

            GeometryDTO geometry = new GeometryDTO("Point", listCoordenates);
            PropertiesDTO properties = new PropertiesDTO();
            feature.add(new FeatureDTO("Feature", properties, geometry));
        }

        return feature;

    }

}
