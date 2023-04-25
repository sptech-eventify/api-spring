package eventify.api_spring.domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "assets/files/img/buffet1/img1.png")
    private String caminho;

    @Schema(example = "img1")
    private String nome;

    @Schema(example = "png")
    private String tipo;

    @Schema(example = "true")
    private boolean isAtivo;

    @Schema(example = "2023-04-25T11:46:23.137Z")
    private LocalDateTime dataUpload;

    @ManyToOne()
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;

    public Imagem(Integer id, String caminho, String nome, String tipo, boolean isAtivo, LocalDateTime dataUpload, Buffet buffet) {
        this.id = id;
        this.caminho = caminho;
        this.nome = nome;
        this.tipo = tipo;
        this.isAtivo = isAtivo;
        this.dataUpload = dataUpload;
        this.buffet = buffet;
    }

    public Imagem() {
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

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }
}
