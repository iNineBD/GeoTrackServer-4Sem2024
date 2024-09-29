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
                        Aplicação Spring Boot para monitoramento de dispositivos IoT.

                        Suporta filtros geoespaciais, como busca por proximidade e rotas, com alta performance em uma base de 500.000 registros.
                        Utiliza Redis para cache e otimização de consultas.
                        """,
                contact = @Contact(
                        name = "iNine",
                        url = "https://github.com/iNineBD",
                        email = "contato@inine.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)

public class OpenAPIConfig {
}
