package eventify.sptech.apispring.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mensagem;
    private LocalDateTime data;
    private Integer remetente;
    private Integer destinatario;
    @ManyToOne
    private Contratante contratante;
    @ManyToOne
    private Proprietario proprietario;
    @ManyToOne
    private Chat chat;

    public Mensagem(Integer id, String mensagem, LocalDateTime data, Integer remetente, Integer destinatario, Contratante contratante, Proprietario proprietario, Chat chat) {
        this.id = id;
        this.mensagem = mensagem;
        this.data = data;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.contratante = contratante;
        this.proprietario = proprietario;
        this.chat = chat;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getRemetente() {
        return remetente;
    }

    public void setRemetente(Integer remetente) {
        this.remetente = remetente;
    }

    public Integer getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Integer destinatario) {
        this.destinatario = destinatario;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
