# restam3: Framework Leve para APIs RESTful em Java

`restam3` é um framework Java minimalista, projetado para a criação rápida e eficiente de APIs RESTful. Ele utiliza **Virtual Threads** (disponíveis no Java 21+) para lidar com concorrência de forma eficiente e oferece um sistema de roteamento simples, manipulação de HTTP e uma arquitetura plugável para serialização/desserialização JSON (com suporte inicial a Jackson).

## ✨ Funcionalidades Principais

* **Servidor HTTP Leve:** Implementação básica de um servidor HTTP (`HttpServer`).
* **Concorrência Otimizada:** Utiliza `Executors.newVirtualThreadPerTaskExecutor()` para processar requisições em **Virtual Threads**, minimizando a sobrecarga de threads.
* **Roteamento Simples:** O `Router` mapeia caminhos (`paths`) de requisição para instâncias de `RestController`.
* **Abstração REST:** Classe base `RestController` com métodos dedicados para cada verbo HTTP (`handleGet`, `handlePost`, etc.).
* **Serialização JSON Plugável:** Interface `JsonParser` que permite a substituição da biblioteca de serialização, com uma implementação padrão usando Jackson (`ConfigurableJacksonParser`).
* **Modelos HTTP Dedicados:** Classes para encapsular Requisições (`HttpRequest`), Respostas (`HttpResponse`), Métodos (`HttpMethod`) e Status (`HttpStatus`).

## 📦 Configuração e Tecnologias

O projeto utiliza Maven para gerenciamento de dependências.

### Adicionando a Dependência Maven

Para usar o `restam3` em seu projeto, adicione a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>io.github.marceltanuri.frameworks</groupId>
        <artifactId>restam3</artifactId>
        <version>1.0.4</version> </dependency>
</dependencies>
```

Nota: O framework restam3 (versão 1.0.4) já inclui as dependências transicionais para Jackson Databind (2.13.0) para JSON e SLF4J (2.0.12) para logging. Você não precisa adicioná-las separadamente, a menos que deseje gerenciar explicitamente suas versões ou configurações.


Pré-requisitos
- Java Development Kit (JDK) 21 ou superior (obrigatório para o uso de Virtual Threads).
- Apache Maven.

### Componentes de Arquitetura

| Classe | Descrição |
| :--- | :--- |
| `HttpServer` | Servidor principal, utiliza Virtual Threads para aceitar conexões e delega ao `Router`. |
| `Router` | Mapeia paths para `RestController`s e executa o método HTTP correto. |
| `RestController` | Classe base para controllers. Injeta `JsonParser` e oferece métodos auxiliares para JSON (`_toJson`, `_fromJson`) e tratamento de erros (`_sendError`). |
| `HttpRequest` | Encapsula a requisição HTTP (método, path, headers, body). |
| `HttpResponse` | Encapsula a resposta HTTP (status, headers, body). Define `Content-Type: application/json` como padrão. |
| `JsonParser` | Interface para desacoplar a serialização JSON. |
| `ConfigurableJacksonParser` | Implementação de `JsonParser` usando Jackson, com suporte a configuração personalizada. |

## 🚀 Como Usar (Exemplo)

Este exemplo demonstra como configurar o servidor e um controlador simples.

### 1. Crie seu Controller

Extenda `RestController` e sobrescreva os métodos de manipulação de verbos HTTP.

```java
import io.github.marceltanuri.frameworks.restam3.controller.RestController;
import io.github.marceltanuri.frameworks.restam3.http.HttpRequest;
import io.github.marceltanuri.frameworks.restam3.http.HttpResponse;
import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser;

public class HelloController extends RestController {
    public HelloController(JsonParser jsonParser) {
        super(jsonParser);
    }

    @Override
    public HttpResponse handleGet(HttpRequest request) {
        // Objeto que será serializado para JSON na resposta
        class SimpleResponse {
            public String status = "OK";
            public String message = "Hello from restam3!";
        }
        
        // Serializa o objeto e retorna a resposta 200 OK
        String jsonBody = _toJson(new SimpleResponse()).orElse(
            "{\"error\": \"Failed to serialize response\"}"
        );
        
        return new HttpResponse(jsonBody, HttpStatus.OK);
    }
}

```

# 2. Inicie o Servidor
Configure o `JsonParser`, registre as rotas no `Router` e inicie o `HttpServer`.

```java
import io.github.marceltanuri.frameworks.restam3.ConfigurableJacksonParser;
import io.github.marceltanuri.frameworks.restam3.HttpServer;
import io.github.marceltanuri.frameworks.restam3.Router;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser;

public class Main {
    public static void main(String[] args) {
        // 1. Configura o JSON Parser (usa Jackson com configurações padrão)
        JsonParser jsonParser = new ConfigurableJacksonParser();

        // 2. Cria o Router e registra as rotas
        Router router = 
            Router
                .create()
                .addRoute("/hello", new HelloController(jsonParser))
                .addRoute("/ready", new HelloController(jsonParser))

        // 3. Inicia o Servidor na porta 8080
        HttpServer
            .create(router)
            .start(8080);
    }
}
```

# Exemplo de Requisição 
Após iniciar o servidor, uma requisição GET para /hello resultará em:

```
curl -i http://localhost:8080/hello
```

## Resposta
```
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: <tamanho_do_body>

{"status": "OK", "message": "Hello from restam3!"}
```