package eventify.api_spring.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class Pesquisa {

   // 'Vila Madalena', 'SP', '12345678',
    @Schema(example = "Rua Andrade de Lima, 124, Vila Madalena")
    private String endereco;

    @Schema()
    private String faixaEtaria;
    @Schema(example = "300")
    private Integer qtdPessoas;

    @Schema(example = "Casamento")
    private String tipoEvento;

    @Schema(example = "4000")
    private Integer orcamento;

    @Schema(example = "2023-05-14 10:30:00")
    private LocalDate dataEvento;

    @Schema(example = "Decoração")
    private String servico;

    @Schema(example = "-23.567890")
        private Double latitude;

    @Schema(example = "-46.789012")
    private Double longitude;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Integer getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Integer orcamento) {
        this.orcamento = orcamento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
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
