package io.github.marceltanuri.frameworks.restam3.demo.repository;

import io.github.marceltanuri.frameworks.restam3.demo.model.Endereco;
import io.github.marceltanuri.frameworks.restam3.demo.model.Geolocalizacao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A repository for CEPs.
 *
 * @author Marcel Tanuri
 */
public class CepRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CepRepository.class);

    private Map<String, Endereco> enderecos = new HashMap<>();

    /**
     * Creates a new CepRepository and loads the CEPs from the CSV file.
     */
    public CepRepository() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/cep.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Endereco endereco = new Endereco();
                endereco.setCep(values[0].replaceAll("\"", "").replaceAll("-", ""));
                endereco.setLogradouro(values[1].replaceAll("\"", ""));
                endereco.setComplemento(values[2].replaceAll("\"", ""));
                endereco.setUnidade(values[3].replaceAll("\"", ""));
                endereco.setBairro(values[4].replaceAll("\"", ""));
                endereco.setLocalidade(values[5].replaceAll("\"", ""));
                endereco.setUf(values[6].replaceAll("\"", ""));
                endereco.setEstado(values[7].replaceAll("\"", ""));
                endereco.setRegiao(values[8].replaceAll("\"", ""));
                endereco.setIbge(values[9].replaceAll("\"", ""));
                endereco.setGia(values[10].replaceAll("\"", ""));
                endereco.setDdd(values[11].replaceAll("\"", ""));
                endereco.setSiafi(values[12].replaceAll("\"", ""));
                endereco.setGeolocalizacao(new Geolocalizacao(values[13].replaceAll("\"", ""), values[14].replaceAll("\"", "")));
                enderecos.put(endereco.getCep(),endereco);
            }
        } catch (IOException e) {
            LOGGER.error("Error reading cep.csv", e);
        }
    }

    /**
     * Finds an address by CEP.
     *
     * @param cep the CEP to search for
     * @return the address, or null if not found
     */
    public Endereco findByCep(String cep) {
        return enderecos.get(cep);
    }
}
