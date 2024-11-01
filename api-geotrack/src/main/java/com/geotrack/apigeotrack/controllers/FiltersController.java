package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.filterusers.RequestUser;
import com.geotrack.apigeotrack.dto.filterusers.ResponseUsers;
import com.geotrack.apigeotrack.service.FiltersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Tag(name = "Listas de Usuários e Dispositivos", description = "Filtros para listar usuários e dispositivos")
@RestController
@RequestMapping("/filters")
public class FiltersController {

    @Autowired
    FiltersService filtersService;

    @Operation(summary = "Retorna uma lista dos usuário com seus dispositivos", description = "Retorna uma lista de " +
            "usuários com seus dispositivos paginadas com o número de elementos por página determinado pela requisição")
    @GetMapping("/users")
    public ResponseEntity<ResponseUsers> filterUsers(@Parameter(description = "Número da página para a paginação", example = "1", required = true) @RequestParam int page,
                                                     @Parameter(description = "Quantidade de itens por página", example = "10", required = true) @RequestParam int qtdPage) throws NoSuchElementException {
        RequestUser request = new RequestUser(page, qtdPage);
        ResponseUsers response = filtersService.listUsers(request);
        return ResponseEntity.ok().body(response);
    }
}
