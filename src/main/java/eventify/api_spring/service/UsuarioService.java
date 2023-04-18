package eventify.api_spring.service;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// Classe que executa toda regra de negócio e retorna os resultados para Controller
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> exibir(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario cadastrar(Usuario usuario) {
        Usuario usuarioCadastrado = usuarioRepository.save(usuario);
        return usuarioCadastrado;
    }
}
