package eventify.api_spring.dto.usuario;

public class UsuarioDevolverDTO {

    private Integer id;

    private String nome;

    private String email;

    private String foto;

    public UsuarioDevolverDTO(Integer id, String nome, String email, String foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public UsuarioDevolverDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
