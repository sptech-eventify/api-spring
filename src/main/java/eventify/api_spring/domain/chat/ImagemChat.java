package eventify.api_spring.domain.chat;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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
}