package eventify.api_spring.domain.usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Integer tipoUsuario;
    private Boolean isAtivo;
    private Boolean isBanido;
    private String foto;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimoLogin;
}
