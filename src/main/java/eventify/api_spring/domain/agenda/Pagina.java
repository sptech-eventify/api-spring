package eventify.api_spring.domain.agenda;

import jakarta.persistence.*;
import java.util.List;

import eventify.api_spring.domain.acesso.Acesso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pagina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String uri;
    
    @OneToMany(mappedBy = "pagina")
    private List<Acesso> acesso;
}
