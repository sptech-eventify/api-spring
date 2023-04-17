package eventify.api_spring.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;

    private Integer tipoUsuario;
    private boolean isAtivo;
    private boolean isBanido;
    @NotBlank
    private String cpf;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimoLogin;

    public Usuario(Integer id, String nome, String email, String senha, Integer tipoUsuario, boolean isAtivo, boolean isBanido, String cpfCnpj, LocalDateTime dataCriacao, LocalDateTime ultimoLogin) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.isAtivo = isAtivo;
        this.isBanido = isBanido;
        this.cpf = cpfCnpj;
        this.dataCriacao = dataCriacao;
        this.ultimoLogin = ultimoLogin;
    }

    public Usuario() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public boolean isBanido() {
        return isBanido;
    }

    public void setBanido(boolean banido) {
        isBanido = banido;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getCpfCnpj() {
        return cpf;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpf = cpfCnpj;
    }
}
