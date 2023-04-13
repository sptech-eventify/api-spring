package eventify.sptech.apispring.Entities;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Buffet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String tamanho;
    private Double precoMedioDiaria;
    private Integer qtdPessoas;
    private String caminhoComprovante;
    private boolean residenciaComprovada;
    private boolean isVisivel;
    @OneToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @ManyToMany
    @JoinTable(name = "buffet_faixa_etaria",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_faixa_etaria"))
    private Set<FaixaEtaria> faixaEtarias = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "buffet_tipo_evento",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_evento"))
    private Set<TipoEvento> tiposEventos = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "buffet_servico",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_servico"))
    private Set<Servico> servicos = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Buffet(Integer id, String nome, String descricao, String tamanho, Double precoMedioDiaria, Integer qtdPessoas, String caminhoComprovante, boolean residenciaComprovada, boolean isVisivel, Endereco endereco, Set<FaixaEtaria> faixaEtarias, Set<TipoEvento> tiposEventos, Set<Servico> servicos, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoMedioDiaria = precoMedioDiaria;
        this.qtdPessoas = qtdPessoas;
        this.caminhoComprovante = caminhoComprovante;
        this.residenciaComprovada = residenciaComprovada;
        this.isVisivel = isVisivel;
        this.endereco = endereco;
        this.faixaEtarias = faixaEtarias;
        this.tiposEventos = tiposEventos;
        this.servicos = servicos;
        this.usuario = usuario;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


    public Double getPrecoMedioDiaria() {
        return precoMedioDiaria;
    }

    public void setPrecoMedioDiaria(Double precoMedioDiaria) {
        this.precoMedioDiaria = precoMedioDiaria;
    }

    public Set<FaixaEtaria> getFaixaEtarias() {
        return faixaEtarias;
    }

    public void setFaixaEtarias(Set<FaixaEtaria> faixaEtarias) {
        this.faixaEtarias = faixaEtarias;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
