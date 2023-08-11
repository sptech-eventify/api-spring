package eventify.api_spring.dto.chat;
import java.time.LocalDateTime;
import java.util.List;

import eventify.api_spring.dto.imagem.ImagemChatDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemDto {

    private Integer id;
    private String mensagem;
    private boolean mandadoPor;
    private LocalDateTime data;
    private Integer idUsuario;
    private Integer idBuffet;
    private List<ImagemChatDto> imagens;
}