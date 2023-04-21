package eventify.api_spring.service;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.UsuarioCadastrarDTO;
import eventify.api_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
// Classe que executa toda regra de negócio e retorna os resultados para Controller
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar(){
        List<Usuario> lista = usuarioRepository.findAll();
        return lista;
    }

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

    public boolean deletar(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()){
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    public UsuarioCadastrarDTO atualizar(int id, Usuario novoUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()){
            UsuarioCadastrarDTO usuario = new UsuarioCadastrarDTO(novoUsuario.getNome(), novoUsuario.getEmail(), novoUsuario.getSenha(),
                    novoUsuario.getCpf(), novoUsuario.getTipoUsuario());
            novoUsuario.setId(id);
            usuarioRepository.save(novoUsuario);
            return usuario;
        } else {
            return null;
        }

    }
}
