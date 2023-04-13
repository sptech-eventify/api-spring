package api.eventify.backend.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Buffet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String tamanho;
    private Double precoMediaDiaria;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private boolean residenciaComprovada;
    private boolean isVisivel;
    @ManyToOne
    private Proprietario proprietario;
    @OneToOne
    private Endereco endereco;
    @ManyToMany
    private List<FaixaEtaria> faixaEtarias;
    @ManyToMany
    private List<TipoEvento> tipoEventos;
    @ManyToMany
    private List<Servico> servicos;

    public Buffet(Integer id, String nome, String descricao, String tamanho, Double precoMediaDiaria, Integer qtdPessoas, String caminhoComprovante, boolean residenciaComprovada, boolean isVisivel, Proprietario proprietario, Endereco endereco, List<FaixaEtaria> faixaEtarias, List<TipoEvento> tipoEventos, List<Servico> servicos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoMediaDiaria = precoMediaDiaria;
        this.qtdPessoas = qtdPessoas;
        this.caminhoComprovante = caminhoComprovante;
        this.residenciaComprovada = residenciaComprovada;
        this.isVisivel = isVisivel;
        this.proprietario = proprietario;
        this.endereco = endereco;
        this.faixaEtarias = faixaEtarias;
        this.tipoEventos = tipoEventos;
        this.servicos = servicos;
    }

    public Buffet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Double getPrecoMediaDiaria() {
        return precoMediaDiaria;
    }

    public void setPrecoMediaDiaria(Double precoMediaDiaria) {
        this.precoMediaDiaria = precoMediaDiaria;
    }

    public Integer getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(Integer qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }

    public String getCaminhoComprovante() {
        return caminhoComprovante;
    }

    public void setCaminhoComprovante(String caminhoComprovante) {
        this.caminhoComprovante = caminhoComprovante;
    }

    public boolean isResidenciaComprovada() {
        return residenciaComprovada;
    }

    public void setResidenciaComprovada(boolean residenciaComprovada) {
        this.residenciaComprovada = residenciaComprovada;
    }

    public boolean isVisivel() {
        return isVisivel;
    }

    public void setVisivel(boolean visivel) {
        isVisivel = visivel;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<FaixaEtaria> getFaixaEtarias() {
        return faixaEtarias;
    }

    public void setFaixaEtarias(List<FaixaEtaria> faixaEtarias) {
        this.faixaEtarias = faixaEtarias;
    }

    public List<TipoEvento> getTipoEventos() {
        return tipoEventos;
    }

    public void setTipoEventos(List<TipoEvento> tipoEventos) {
        this.tipoEventos = tipoEventos;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
