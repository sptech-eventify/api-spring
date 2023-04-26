package eventify.api_spring.dto.usuario;

import eventify.api_spring.domain.*;
import eventify.api_spring.dto.ImagemDTO;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuffetDto {
    private Integer id;
    private String nome;
    private String descricao;
    private String tamanho;
    private Double precoMedioDiaria;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private boolean isVisivel;
    private Endereco endereco;
    private Set<FaixaEtaria> faixaEtarias;
    private Set<TipoEvento> tipoEventos;
    private Set<Servico> servicos;
    private List<ImagemDTO> imagens;

    private List<Agenda> agendas;

    public BuffetDto(Integer id, String nome, String descricao, String tamanho, Double precoMedioDiaria, Integer qtdPessoas, String caminhoComprovante, boolean isVisivel, Endereco endereco, Set<FaixaEtaria> faixaEtarias, Set<TipoEvento> tipoEventos, Set<Servico> servicos, List<ImagemDTO> imagens, List<Agenda> agendas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoMedioDiaria = precoMedioDiaria;
        this.qtdPessoas = qtdPessoas;
        this.caminhoComprovante = caminhoComprovante;
        this.isVisivel = isVisivel;
        this.endereco = endereco;
        this.faixaEtarias = faixaEtarias;
        this.tipoEventos = tipoEventos;
        this.servicos = servicos;
        this.imagens = imagens;
        this.agendas = agendas;
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

    public Double getPrecoMedioDiaria() {
        return precoMedioDiaria;
    }

    public void setPrecoMedioDiaria(Double precoMedioDiaria) {
        this.precoMedioDiaria = precoMedioDiaria;
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

    public boolean isVisivel() {
        return isVisivel;
    }

    public void setVisivel(boolean visivel) {
        isVisivel = visivel;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<FaixaEtaria> getFaixaEtarias() {
        return faixaEtarias;
    }

    public void setFaixaEtarias(Set<FaixaEtaria> faixaEtarias) {
        this.faixaEtarias = faixaEtarias;
    }

    public Set<TipoEvento> getTipoEventos() {
        return tipoEventos;
    }

    public void setTipoEventos(Set<TipoEvento> tipoEventos) {
        this.tipoEventos = tipoEventos;
    }

    public Set<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Set<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<ImagemDTO> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemDTO> imagens) {
        this.imagens = imagens;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }
}
