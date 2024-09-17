package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.StopPoint.*;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StopPointService {

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    LocalizacaoRepository localizacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // oque vou retornar                       parametro = oque vou receber
    public StopPointResponseDTO stopPointTeste(StopPointRequestDTO requestDTO) {

        Optional<Dispositivo> userDevice = dispositivoRepository.findByUsuarioIdUsuario(requestDTO.user());

        String nameUser = userDevice.get().getUsuario().getNome();
        String nameDevice = userDevice.get().getNome();

        if (userDevice.isEmpty()) {
            throw new RuntimeException("Nenhum dispositivo encontrado");
        }

        return new StopPointResponseDTO(nameUser, nameDevice);
    }
}
