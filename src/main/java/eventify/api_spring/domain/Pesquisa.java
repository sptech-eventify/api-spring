package eventify.api_spring.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public class Pesquisa {
    @Schema(example = "Buffet")
    private String nome;

    @Schema(example = "1 a 5 anos")
    private List<String> faixaEtaria;

    @Schema(example = "100")
    private Integer tamanho;

    @Schema(example = "300")
    private Integer qtdPessoas;

    @Schema(example = "Casamento")
    private List<String> tipoEvento;

    @Schema(example = "1000.00")
    private Double orcMin;

    @Schema(example = "2000.00")
    private Double orcMax;

    @Schema(example = "2023-05-14 10:30:00")
    private LocalDate dataEvento;

    @Schema(example = "Decoração")
    private List<String> servico;

    @Schema(example = "-23.567890")
    private Double latitude;

    @Schema(example = "-46.789012")
    private Double longitude;

    public Pesquisa(String nome, List<String> faixaEtaria, Integer tamanho, Integer qtdPessoas, List<String> tipoEvento, Double orcMin, Double orcMax, LocalDate dataEvento, List<String> servico, Double latitude, Double longitude) {
        this.nome = nome;
        this.faixaEtaria = faixaEtaria;
        this.tamanho = tamanho;
        this.qtdPessoas = qtdPessoas;
        this.tipoEvento = tipoEvento;
        this.orcMin = orcMin;
        this.orcMax = orcMax;
        this.dataEvento = dataEvento;
        this.servico = servico;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(List<String> faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public List<String> getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(List<String> tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Double getOrcMin() {
        return orcMin;
    }

    public void setOrcMin(Double orcMin) {
        this.orcMin = orcMin;
    }

    public Double getOrcMax() {
        return orcMax;
    }

    public void setOrcMax(Double orcMax) {
        this.orcMax = orcMax;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public List<String> getServico() {
        return servico;
    }

    public void setServico(List<String> servico) {
        this.servico = servico;
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

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return "Pesquisa{" +
                "nome='" + nome + '\'' +
                ", faixaEtaria=" + faixaEtaria +
                ", tamanho=" + tamanho +
                ", qtdPessoas=" + qtdPessoas +
                ", tipoEvento=" + tipoEvento +
                ", orcMin=" + orcMin +
                ", orcMax=" + orcMax +
                ", dataEvento=" + dataEvento +
                ", servico=" + servico +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
