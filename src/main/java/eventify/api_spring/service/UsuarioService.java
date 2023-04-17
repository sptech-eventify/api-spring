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
    private static UsuarioRepository repository;
    public static Optional<Usuario> exibir(Integer id){
        return repository.findById(id);
    }

    public static Usuario cadastrar(Usuario usuario){
        repository.save(usuario);
        return usuario;
    }
}
