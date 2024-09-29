# GeoTrack
Este projeto é uma API desenvolvida em Java Spring que fornece dados de geolocalização a partir de uma base de dados de 500.000 registros. A API é projetada para responder com dados em formato GEOJSON e é adequada para visualização em mapas.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter os seguintes itens instalados:

- [JDK 18 ou superior](https://openjdk.java.net/install/)
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://docs.docker.com/get-docker/)

## Clonando o Repositório

Clone este repositório usando o comando:

```bash
git clone https://github.com/iNineBD/GeoTrackServer-4Sem2024.git
cd GeoTrackServer-4Sem2024.git
```

## Configuração do Banco de Dados

Configure a conexão no arquivo `src/main/resources/external.properties` com as credenciais corretas:

```properties
DATABASE_URL=<tns cloud>
DATABASE_USER=<usuário>
DATABASE_PASSWORD=<senha>
```

### Executando o Redis em Docker

Rode o comando abaixo via terminal. Certifique-se de estar no mesmo diretório do arquivo `docker-compose.yml`:

```bash
docker-compose up -d
```

## Executando a Aplicação

Para executar a aplicação, utilize o seguinte comando:

```bash
./mvnw spring-boot:run
```

Se você não tiver o Maven Wrapper, pode usar o comando Maven normal:

```bash
mvn spring-boot:run
```

## Testando a API

Após a aplicação estar rodando, você pode testar a API usando ferramentas como [Swagger](http://localhost:8080/swagger-ui/index.html#/)

A API estará disponível em:

```
http://localhost:8080/
```
