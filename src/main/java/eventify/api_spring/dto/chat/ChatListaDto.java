package eventify.api_spring.dto.chat;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListaDto {

    private Integer id;
    private String nome;
    private String mensagem;
    private LocalTime data;

    public ChatListaDto(Integer id, String nome, String mensagem, LocalDateTime data) {
        this.id = id;
        this.nome = nome;
        this.mensagem = mensagem;
        this.data = data.toLocalTime();
    }
}
