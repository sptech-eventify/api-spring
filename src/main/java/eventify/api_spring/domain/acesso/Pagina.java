package eventify.api_spring.domain.acesso;

import jakarta.persistence.*;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pagina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String uri;
    
    @OneToMany(mappedBy = "pagina")
    private List<Acesso> acesso;

    public Pagina(String nome, String uri) {
        this.nome = nome;
        this.uri = uri;
    }
}