package eventify.api_spring.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalTime getData() {
        return data;
    }

    public void setData(LocalTime data) {
        this.data = data;
    }
}
