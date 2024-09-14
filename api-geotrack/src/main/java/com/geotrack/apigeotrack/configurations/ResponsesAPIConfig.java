package com.geotrack.apigeotrack.configurations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Processamento efetuado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validacao"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "404", description = "Não encontrado"),
        @ApiResponse(responseCode = "405", description = "Solicitacao HTTP nao corresponde a requisicao"),
        @ApiResponse(responseCode = "415", description = "Tipo de midia da solicitacao não suportado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
}
)

public @interface ResponsesAPIConfig {
}

