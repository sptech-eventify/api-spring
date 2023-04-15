package eventify.api_spring.dto;
import java.time.LocalDateTime;
import java.util.List;

public class MensagemDto {

    private Integer id;
    private String mensagem;
    private boolean mandadoPor;
    private LocalDateTime data;
    private Integer idUsuario;
    private Integer idBuffet;
    private List<ImagemChatDto> imagens;

    public MensagemDto(Integer id, String mensagem, boolean mandadoPor, LocalDateTime data, Integer idUsuario, Integer idBuffet, List<ImagemChatDto> imagens) {
        this.id = id;
        this.mensagem = mensagem;
        this.mandadoPor = mandadoPor;
        this.data = data;
        this.idUsuario = idUsuario;
        this.idBuffet = idBuffet;
        this.imagens = imagens;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdBuffet() {
        return idBuffet;
    }

    public void setIdBuffet(Integer idBuffet) {
        this.idBuffet = idBuffet;
    }

    public List<ImagemChatDto> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemChatDto> imagens) {
        this.imagens = imagens;
    }
}
