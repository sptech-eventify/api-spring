package eventify.api_spring.domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "true")
    private boolean isValidado;

    @Schema(example = "R. Gualaxo")
    private String logradouro;

    @Schema(example = "285")
    private Integer numero;

    @Schema(example = "Aclimação")
    private String bairro;

    @Schema(example = "SP")
    private String uf;

    @Schema(example = "01533-010")
    @NotBlank
    private String cep;

    @Schema(example = "-23.574011")
    private Double latitude;

    @Schema(example = "46.6388203")
    private Double longitude;

    public Endereco(Integer id, boolean isValidado, String logradouro, Integer numero, String bairro, String uf, String cep, Double latitude, Double longitude) {
        this.id = id;
        this.isValidado = isValidado;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.uf = uf;
        this.cep = cep;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Endereco() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isValidado() {
        return isValidado;
    }

    public void setValidado(boolean validado) {
        isValidado = validado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}