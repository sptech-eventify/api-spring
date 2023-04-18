package eventify.api_spring.domain;
import eventify.api_spring.dto.ImagemChatDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mensagem;
    private boolean mandadoPor;
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;
    @OneToMany(mappedBy = "mensagem")
    private List<ImagemChat> imagens = new ArrayList<>();

    public Mensagem(Integer id, String mensagem, boolean mandadoPor, LocalDateTime data, Usuario usuario, Buffet buffet, List<ImagemChat> imagens) {
        this.id = id;
        this.mensagem = mensagem;
        this.mandadoPor = mandadoPor;
        this.data = data;
        this.usuario = usuario;
        this.buffet = buffet;
        this.imagens = imagens;
    }

    public Mensagem(Integer id, String mensagem, boolean mandadoPor, LocalDateTime data, Usuario usuario, Buffet buffet) {
        this.id = id;
        this.mensagem = mensagem;
        this.mandadoPor = mandadoPor;
        this.data = data;
        this.usuario = usuario;
        this.buffet = buffet;
    }

    public Mensagem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isMandadoPor() {
        return mandadoPor;
    }

    public void setMandadoPor(boolean mandadoPor) {
        this.mandadoPor = mandadoPor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }

    public List<ImagemChat> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemChat> imagens) {
        this.imagens = imagens;
    }

    public List<ImagemChatDto> getImagensDto() {
        return imagens.stream()
                .map(i -> new ImagemChatDto(i.getId(),i.getCaminho(),i.getNome(),i.getTipo(),i.isAtivo(),i.getDataUpload()))
                .toList();
    }
}
