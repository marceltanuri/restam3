package io.github.marceltanuri.frameworks.restam3;

import io.github.marceltanuri.frameworks.restam3.demo.controller.CepRestController;
import io.github.marceltanuri.frameworks.restam3.demo.repository.CepRepository;

import io.github.marceltanuri.frameworks.restam3.Router;
import io.github.marceltanuri.frameworks.restam3.HttpServer;

import io.github.marceltanuri.frameworks.restam3.json.JsonParser;
import io.github.marceltanuri.frameworks.restam3.json.ConfigurableJacksonParser;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;


/**
 * A demo application to show how to use the framework.
 *
 * @author Marcel Tanuri
 */
public class Demo {

    private Demo() {
    }

    /**
     * The main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JsonParser jsonParser = new ConfigurableJacksonParser(mapper -> {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        });

        HttpServer.create(
            Router.create()
            .addRoute(
                Arrays.asList("/cep", "address"), 
                new CepRestController(new CepRepository(), jsonParser))
            .addRoute(
                "/test", 
                new CepRestController(new CepRepository(), jsonParser))
        )
        .start(8080);
    }
}
