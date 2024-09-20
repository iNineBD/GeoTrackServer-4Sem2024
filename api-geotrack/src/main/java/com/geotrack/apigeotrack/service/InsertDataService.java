package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.insertData.RequestInsert;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.DispositivoRepository;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class InsertDataService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    LocalizacaoRepository localizacaoRepository;
    @Autowired
    DispositivoRepository dispositivoRepository;

    @Transactional
    public void insertDataService(List<RequestInsert> requestInserts) throws Exception {
        List<Localizacao> listAll = new ArrayList<>();
        for (RequestInsert requestInsert : requestInserts) {

            Optional<Usuario> usuarioExistente = usuarioRepository.findById(Integer.valueOf(requestInsert.idUsuario()));

            if (usuarioExistente.isEmpty()) {
                throw new NoSuchElementException("Nenhum usuario encontrado");
            }

            Localizacao localizacao = new Localizacao();
            localizacao.setDispositivo(usuarioExistente.get().getDispositivos().getFirst());
            localizacao.setDataHora(Timestamp.valueOf(requestInsert.dataHora()));
            localizacao.setLatitude(requestInsert.latitude());
            localizacao.setLongitude(requestInsert.longitude());
            localizacao.setIdBaseCliente(requestInsert.idBaseCliente());
            listAll.add(localizacao);
        }

        localizacaoRepository.saveAll(listAll);
    }
}