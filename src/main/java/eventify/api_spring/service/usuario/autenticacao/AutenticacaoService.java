package eventify.api_spring.service.usuario.autenticacao;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.usuario.dto.UsuarioDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
