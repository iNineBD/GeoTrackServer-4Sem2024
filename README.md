# Back-end GeoTrack
Este projeto é uma API desenvolvida em Java Spring que fornece dados de geolocalização a partir de uma base de dados de 500.000 registros. A API é projetada para responder com dados em formato GEOJSON e é adequada para visualização em mapas.

## Guia de Instalação e Acesso ao Projeto GeoTrack

Este guia irá ensinar como baixar e executar o projeto **GeoTrack** utilizando Docker e como acessar a aplicação através do navegador.

## Pré-requisitos

Antes de começar, certifique-se de que:

1. **Docker** está instalado em sua máquina.  
   Caso não esteja, siga as instruções em [Docker Documentation](https://docs.docker.com/get-docker/).

2. Você tem uma conexão ativa com a internet para baixar as imagens necessárias.

---

## Escolha da Versão da Imagem

A versão da imagem do GeoTrack depende da sprint atual do projeto. Certifique-se de usar a versão correspondente especificada no formato `:<versão>` no comando `docker pull`.  
Por exemplo, para a segunda sprint, utilize `:2.0`.

---

## Passos para Baixar e Executar

### 1. Baixar as Imagens do Docker Hub

Abra o terminal e execute os comandos abaixo para baixar as imagens do **GeoTrack** e do **Redis**:

```bash
docker pull inineapi/geotrack4sem:<versão>
docker pull inineapi/redis:latest
```

### 2. Executar as Imagens no Docker

Depois de baixar as imagens, execute os containers para iniciar a aplicação. Utilize o comando abaixo para iniciar os containers:

```bash
docker run -d --name geotrack-api -p 8080:8080 inineapi/geotrack4sem:<versão>
docker run -d --name redis -p 6379:6379 inineapi/redis:latest
```

### 3. Acessar a Aplicação no Navegador

Após os containers estarem em execução, abra o navegador de sua preferência e digite o seguinte endereço:

```
http://localhost:8080
```

---

Pronto! Agora você está preparado para explorar os dados de geolocalização do GeoTrack e utilizá-los em sua aplicação ou para visualização em mapas.
