package eventify.api_spring.domain.chat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.imagem.ImagemChatDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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

    public Mensagem(Integer id, String mensagem, boolean mandadoPor, LocalDateTime data, Usuario usuario, Buffet buffet) {
        this.id = id;
        this.mensagem = mensagem;
        this.mandadoPor = mandadoPor;
        this.data = data;
        this.usuario = usuario;
        this.buffet = buffet;
    }

    public List<ImagemChatDto> getImagensDto() {
        return imagens.stream()
                .map(i -> new ImagemChatDto(i.getId(),i.getCaminho(),i.getNome(),i.getTipo(),i.isAtivo(),i.getDataUpload()))
                .toList();
    }
}
