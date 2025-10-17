package io.github.marceltanuri.frameworks.restam3.demo.model;

/**
 * Represents an address.
 *
 * @author Marcel Tanuri
 */
public class Endereco {
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    private Geolocalizacao geolocalizacao;

    /**
     * Creates a new empty address.
     */
    public Endereco() {
    }

    /**
     * Gets the CEP.
     *
     * @return the CEP
     */
    public String getCep() {
        return cep;
    }

    /**
     * Sets the CEP.
     *
     * @param cep the CEP
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Gets the logradouro.
     *
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Sets the logradouro.
     *
     * @param logradouro the logradouro
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * Gets the complemento.
     *
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Sets the complemento.
     *
     * @param complemento the complemento
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * Gets the unidade.
     *
     * @return the unidade
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Sets the unidade.
     *
     * @param unidade the unidade
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * Gets the bairro.
     *
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Sets the bairro.
     *
     * @param bairro the bairro
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Gets the localidade.
     *
     * @return the localidade
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Sets the localidade.
     *
     * @param localidade the localidade
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Gets the UF.
     *
     * @return the UF
     */
    public String getUf() {
        return uf;
    }

    /**
     * Sets the UF.
     *
     * @param uf the UF
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * Gets the estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado the estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Gets the regiao.
     *
     * @return the regiao
     */
    public String getRegiao() {
        return regiao;
    }

    /**
     * Sets the regiao.
     *
     * @param regiao the regiao
     */
    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    /**
     * Gets the IBGE code.
     *
     * @return the IBGE code
     */
    public String getIbge() {
        return ibge;
    }

    /**
     * Sets the IBGE code.
     *
     * @param ibge the IBGE code
     */
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    /**
     * Gets the GIA code.
     *
     * @return the GIA code
     */
    public String getGia() {
        return gia;
    }

    /**
     * Sets the GIA code.
     *
     * @param gia the GIA code
     */
    public void setGia(String gia) {
        this.gia = gia;
    }

    /**
     * Gets the DDD code.
     *
     * @return the DDD code
     */
    public String getDdd() {
        return ddd;
    }

    /**
     * Sets the DDD code.
     *
     * @param ddd the DDD code
     */
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    /**
     * Gets the SIAFI code.
     *
     * @return the SIAFI code
     */
    public String getSiafi() {
        return siafi;
    }

    /**
     * Sets the SIAFI code.
     *
     * @param siafi the SIAFI code
     */
    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }

    /**
     * Gets the geolocation.
     *
     * @return the geolocation
     */
    public Geolocalizacao getGeolocalizacao() {
        return geolocalizacao;
    }

    /**
     * Sets the geolocation.
     *
     * @param geolocalizacao the geolocation
     */
    public void setGeolocalizacao(Geolocalizacao geolocalizacao) {
        this.geolocalizacao = geolocalizacao;
    }

    @Override
    public String toString() { 
        return "Endereco{" +
                "cep='" + cep + "'" +      
                ", logradouro='" + logradouro + "'" + 
                ", complemento='" + complemento + "'" + 
                ", unidade='" + unidade + "'" + 
                ", bairro='" + bairro + "'" + 
                ", localidade='" + localidade + "'" + 
                ", uf='" + uf + "'" + 
                ", estado='" + estado + "'" + 
                ", regiao='" + regiao + "'" +      
                ", ibge='" + ibge + "'" + 
                ", gia='" + gia + "'" + 
                ", ddd='" + ddd + "'" + 
                ", siafi='" + siafi + "'" + 
                ", geolocalizacao=" + geolocalizacao + 
                '}';
    }   
}
