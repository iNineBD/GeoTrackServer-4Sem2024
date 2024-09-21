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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(new File(pathToFile));
        jsonParser.nextToken();

        List<Localizacao> insertAll = new ArrayList<>();
        while (jsonParser.nextToken() != null) {
            RequestInsert requestInsert = objectMapper.readValue(jsonParser, RequestInsert.class);
            Optional<Usuario> user = usuarioRepository.findById(Integer.valueOf(requestInsert.idUsuario()));

            if (user.isEmpty()){
                continue;
            }

            Localizacao localizacao = new Localizacao(requestInsert.idBaseCliente(), requestInsert.latitude(), requestInsert.longitude(), requestInsert.dataHora(), user.get().getDispositivos().getFirst());
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
