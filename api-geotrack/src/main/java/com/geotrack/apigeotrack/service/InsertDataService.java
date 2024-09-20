package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.insertData.RequestInsert;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InsertDataService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    LocalizacaoRepository localizacaoRepository;
    @Autowired
    DispositivoRepository dispositivoRepository;

    public void insertDataService(List<RequestInsert> requestInserts) throws Exception {
        for (RequestInsert requestInsert : requestInserts) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(Integer.valueOf(requestInsert.idUsuario()));
            Usuario usuario;
            Dispositivo dispositivo;
            if (usuarioExistente.isPresent()) {
                usuario = usuarioExistente.get();
                usuario.getDispositivos();
                Localizacao localizacao = new Localizacao();
                localizacao.getDispositivo();
                localizacao.setIdBaseCliente(requestInsert.idBaseCliente());
                localizacao.setDataHora(requestInsert.dataHora());
                localizacao.setLatitude(requestInsert.latitude());
                localizacao.setLongitude(requestInsert.longitude());
                localizacaoRepository.save(localizacao);
            } else {
                usuario = new Usuario();
                usuario.setNome(requestInsert.nome());
                usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioRepository.save(usuario);
                dispositivo = new Dispositivo();
                dispositivo.setUsuario(usuario);
                dispositivoRepository.save(dispositivo);
                Localizacao localizacao = new Localizacao();
                localizacao.setIdBaseCliente(requestInsert.idBaseCliente());
                localizacao.setDataHora(requestInsert.dataHora());
                localizacao.setLatitude(requestInsert.latitude());
                localizacao.setLongitude(requestInsert.longitude());
                localizacao.setDispositivo(dispositivo);
                localizacaoRepository.save(localizacao);

            }
        }

    }
}