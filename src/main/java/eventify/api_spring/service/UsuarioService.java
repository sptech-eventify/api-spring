package eventify.api_spring.service;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.UsuarioCadastrarDTO;
import eventify.api_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
// Classe que executa toda regra de negócio e retorna os resultados para Controller
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> exibir(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario cadastrar(UsuarioCadastrarDTO novoUsuario) {
        Usuario usuario = new Usuario(
                novoUsuario.nome(), novoUsuario.email(), novoUsuario.senha(), novoUsuario.cpf()
                , novoUsuario.tipoUsuario(), true, false, LocalDateTime.now(), LocalDateTime.now()
        );
        return usuarioRepository.save(usuario);
    }
}
