package eventify.api_spring.dto.buffet;
import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.dto.agenda.AgendaDto;
import eventify.api_spring.dto.imagem.ImagemDTO;

import java.util.List;
import java.util.Set;

public class BuffetDtoResposta {
    private Integer id;
    private String nome;
    private String descricao;
    private Double precoMedioDiaria;
    private Endereco endereco;
    private Integer tamanho;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private Boolean residenciaComprovada;
    private Set<FaixaEtaria> faixasEtarias;
    private Set<TipoEvento> tiposEventos;
    private Set<Servico> servicos;
    private String nomeProprietario;
    private List<AgendaDto> agendas;
    private List<ImagemDTO> imagens;

    public BuffetDtoResposta(Integer id, String nome, String descricao, Double precoMedioDiaria, Endereco endereco, Integer tamanho, Integer qtdPessoas, String caminhoComprovante, Boolean residenciaComprovada, Set<FaixaEtaria> faixasEtarias, Set<TipoEvento> tiposEventos, Set<Servico> servicos, String nomeProprietario, List<AgendaDto> agendas, List<ImagemDTO> imagens) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoMedioDiaria = precoMedioDiaria;
        this.endereco = endereco;
        this.tamanho = tamanho;
        this.qtdPessoas = qtdPessoas;
        this.caminhoComprovante = caminhoComprovante;
        this.residenciaComprovada = residenciaComprovada;
        this.faixasEtarias = faixasEtarias;
        this.tiposEventos = tiposEventos;
        this.servicos = servicos;
        this.nomeProprietario = nomeProprietario;
        this.agendas = agendas;
        this.imagens = imagens;
    }

    public BuffetDtoResposta() {

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

    public Double getPrecoMedioDiaria() {
        return precoMedioDiaria;
    }

    public void setPrecoMedioDiaria(Double precoMedioDiaria) {
        this.precoMedioDiaria = precoMedioDiaria;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
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

    public Boolean getResidenciaComprovada() {
        return residenciaComprovada;
    }

    public void setResidenciaComprovada(Boolean residenciaComprovada) {
        this.residenciaComprovada = residenciaComprovada;
    }

    public Set<FaixaEtaria> getFaixasEtarias() {
        return faixasEtarias;
    }

    public void setFaixasEtarias(Set<FaixaEtaria> faixasEtarias) {
        this.faixasEtarias = faixasEtarias;
    }

    public Set<TipoEvento> getTiposEventos() {
        return tiposEventos;
    }

    public void setTiposEventos(Set<TipoEvento> tiposEventos) {
        this.tiposEventos = tiposEventos;
    }

    public Set<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Set<Servico> servicos) {
        this.servicos = servicos;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public List<AgendaDto> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaDto> agendas) {
        this.agendas = agendas;
    }

    public List<ImagemDTO> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemDTO> imagens) {
        this.imagens = imagens;
    }
}
