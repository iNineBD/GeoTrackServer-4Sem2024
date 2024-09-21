package com.geotrack.apigeotrack.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.geotrack.apigeotrack.dto.insertdata.RequestInsert;
import com.geotrack.apigeotrack.entities.Localizacao;
import com.geotrack.apigeotrack.entities.Usuario;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PopulateService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LocalizacaoRepository localizacaoRepository;

    public void populate(String pathToFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        JsonParser jsonParser = new JsonFactory().createParser(new File(pathToFile));
        jsonParser.nextToken();

        List<Localizacao> insertAll = new ArrayList<>();
        while (jsonParser.nextToken() != null) {
            RequestInsert requestInsert = objectMapper.readValue(jsonParser, RequestInsert.class);

            Optional<Usuario> user = usuarioRepository.findById(Integer.valueOf(requestInsert.idUsuario()));

            if (user.isEmpty()){
                System.out.println("User not found: " + requestInsert.idUsuario());
                continue;
            }

            if (user.get().getDispositivos().isEmpty()) {
                System.out.println("Dispositivos not found para usuario: " + requestInsert.idUsuario());
                continue;
            }

            Localizacao localizacao = new Localizacao(requestInsert.idBaseCliente(), requestInsert.latitude(), requestInsert.longitude(), requestInsert.dataHora(), user.get().getDispositivos().getFirst());

            if (localizacao.getIdLocalizacao().isEmpty()){
                System.out.println("IDLocal not found para usuario: " + requestInsert.idUsuario());
            }

            insertAll.add(localizacao);

            if (insertAll.size() >= 500){
                LocalDateTime agora = LocalDateTime.now();
                localizacaoRepository.saveAll(insertAll);
                System.out.println("Levou: " + Duration.between(agora, LocalDateTime.now()).toString());
                insertAll.clear();
            }
        }




    }

}
