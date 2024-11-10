package com.geotrack.apigeotrack.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "GeoTrack",
                version = "v1",
                description = """
                        Aplicação para visualização de dados geográficos a partir de dispositivos IoT.

                        Aplicação consiste em desenvolver interações com mapas, permitindo a visualização de dispositivos IoT em tempo real,
                        além de permitir a criação de áreas geográficas.
                        """,
                contact = @Contact(
                        name = "iNine",
                        url = "https://github.com/iNineBD",
                        email = "apibdfatec@gmail.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)

public class OpenAPIConfig {
}
