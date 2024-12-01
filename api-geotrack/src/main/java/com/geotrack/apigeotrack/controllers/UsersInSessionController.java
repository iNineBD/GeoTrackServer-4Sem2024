package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.usersInSession.RequestUsersInSessionDTO;
import com.geotrack.apigeotrack.dto.usersInSession.ResponseUsersInSessionDTO;
import com.geotrack.apigeotrack.service.UsersInSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Usuários em Sessão", description = "Operação que retorna nome dos usuarios que contem localizações na sessão escolhida")
@RestController
@RequestMapping("/usersInSession")
@CrossOrigin(origins = "*")
public class UsersInSessionController {

    @Autowired
    UsersInSessionService usersInSessionService;

    @GetMapping
    public ResponseEntity<ResponseUsersInSessionDTO> userInSession(RequestUsersInSessionDTO requestUsersInSessionDTO) {
        return ResponseEntity.ok().body(usersInSessionService.listUsersInSession(requestUsersInSessionDTO));
    }



}
