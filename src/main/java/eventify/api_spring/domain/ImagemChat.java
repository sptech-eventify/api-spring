package eventify.api_spring.domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ImagemChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String caminho;
    private String nome;
    private String tipo;
    private boolean isAtivo;
    private LocalDateTime dataUpload;
    @ManyToOne
    @JoinColumn(name = "id_mensagem")
    private Mensagem mensagem;

    public ImagemChat(Integer id, String caminho, String nome, String tipo, boolean isAtivo, LocalDateTime dataUpload, Mensagem mensagem, Usuario usuario) {
        this.id = id;
        this.caminho = caminho;
        this.nome = nome;
        this.tipo = tipo;
        this.isAtivo = isAtivo;
        this.dataUpload = dataUpload;
    }

    public ImagemChat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
