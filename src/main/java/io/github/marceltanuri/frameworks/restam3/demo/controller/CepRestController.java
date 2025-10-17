package io.github.marceltanuri.frameworks.restam3.demo.controller;

import io.github.marceltanuri.frameworks.restam3.demo.repository.CepRepository;
import io.github.marceltanuri.frameworks.restam3.http.HttpRequest;
import io.github.marceltanuri.frameworks.restam3.http.HttpResponse;
import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser; 
import io.github.marceltanuri.frameworks.restam3.controller.RestController;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A REST controller for handling CEP requests.
 *
 * @author Marcel Tanuri
 */
public class CepRestController extends RestController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CepRestController.class);

    private final CepRepository cepRepository;

    /**
     * Cria um novo CepRestController.
     *
     * @param cepRepository o repositório CEP
     * @param jsonParser O parser de JSON injetado para a classe base
     */
    public CepRestController(CepRepository cepRepository, JsonParser jsonParser) {
        super(jsonParser); 
        this.cepRepository = cepRepository;
    }

    /**
     * Handles GET requests for CEPs.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    @Override
    public HttpResponse handleGet(HttpRequest request) {
        String cep = request.getPath().substring(5);
        
        return Optional.ofNullable(cepRepository.findByCep(cep))
                .flatMap(this::_toJson)
                .map(json -> new HttpResponse(json, HttpStatus.OK))
                .orElse(_sendError("CEP não encontrado", HttpStatus.NOT_FOUND));
    }
}