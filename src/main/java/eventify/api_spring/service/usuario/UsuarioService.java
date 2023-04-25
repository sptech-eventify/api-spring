package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioMapper;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.usuario.dto.UsuarioLoginDto;
import eventify.api_spring.service.usuario.dto.UsuarioTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
// Classe que executa toda regra de negócio e retorna os resultados para Controller
public class UsuarioService {
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar(){
        List<Usuario> lista = usuarioRepository.findAll();
        return lista;
    }

    public Optional<Usuario> exibir(Integer id) {
        return usuarioRepository.findById(id);
    }

    public void cadastrar(UsuarioCadastrarDTO usuarioCadastrarDTO) {
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCadastrarDTO);
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
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
            novoUsuario.setId(id);

            UsuarioCadastrarDTO usuario = new UsuarioCadastrarDTO();

//            novoUsuario.setNome(usuarioOpt.get().getNome());
            usuario.setNome(novoUsuario.getNome());

//            novoUsuario.setCpf(usuarioOpt.get().getCpf());
            usuario.setCpf(novoUsuario.getCpf());

//            novoUsuario.setTipoUsuario(usuarioOpt.get().getTipoUsuario());
            usuario.setTipoUsuario(novoUsuario.getTipoUsuario());

//            novoUsuario.setEmail(usuarioOpt.get().getEmail());
            usuario.setEmail(novoUsuario.getEmail());

//            novoUsuario.setAtivo(usuarioOpt.get().isAtivo());
            usuario.setSenha(novoUsuario.getSenha());

//            novoUsuario.setBanido(usuarioOpt.get().isBanido());
            usuarioRepository.save(novoUsuario);
            return usuario;
        } else {
            return null;
        }
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }
}
