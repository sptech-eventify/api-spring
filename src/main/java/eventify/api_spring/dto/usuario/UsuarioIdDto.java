package eventify.api_spring.dto.usuario;
public class UsuarioIdDto {

    private Integer id;

    public UsuarioIdDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
