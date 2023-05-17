package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
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

    public List<Usuario> listar() {
        List<Usuario> lista = usuarioRepository.findAll();
        return lista;
    }

    public Optional<Usuario> exibir(Integer id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDevolverDTO cadastrar(UsuarioCadastrarDTO usuarioCadastrarDTO) {
        UsuarioDevolverDTO usuarioDevolverDTO = new UsuarioDevolverDTO();
        Usuario novoUsuario = UsuarioMapper.of(usuarioCadastrarDTO);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        this.usuarioRepository.save(novoUsuario);
        usuarioDevolverDTO.setId(novoUsuario.getId());
        usuarioDevolverDTO.setNome(novoUsuario.getNome());
        usuarioDevolverDTO.setEmail(novoUsuario.getEmail());
        return usuarioDevolverDTO;
    }

    public Boolean banir(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    public UsuarioCadastrarDTO atualizar(int id, Usuario novoUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        UsuarioCadastrarDTO usuario = new UsuarioCadastrarDTO();
        if (usuarioOpt.isPresent()) {
            if (novoUsuario.getNome() != null) {
                usuario.setNome(novoUsuario.getNome());
                usuarioOpt.get().setNome(novoUsuario.getNome());
            }
            if (novoUsuario.getEmail() != null) {
                usuario.setEmail(novoUsuario.getEmail());
                usuarioOpt.get().setEmail(novoUsuario.getEmail());
            }
            if (novoUsuario.getCpf() != null) {
                usuario.setCpf(novoUsuario.getCpf());
                usuarioOpt.get().setCpf(novoUsuario.getCpf());
            }
            if (novoUsuario.getSenha() != null) {
                usuario.setSenha(novoUsuario.getSenha());
                usuarioOpt.get().setSenha(novoUsuario.getSenha());
            }
            usuarioOpt.get().setAtivo(usuarioOpt.get().isAtivo());
            usuarioOpt.get().setBanido(usuarioOpt.get().isBanido());
        } else {
            throw new ResponseStatusException(404, "Usuário não encontrado", null);
        }
        usuarioRepository.save(usuarioOpt.get());
        return usuario;
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLoginDto.getEmail());
        if (usuario.isPresent()) {
            if (usuario.get().isBanido()) {
                throw new ResponseStatusException(403, "Usuário banido", null);
            } else {
                usuario.get().setAtivo(true);
                usuario.get().setBanido(false);
                usuario.get().setUltimoLogin(LocalDateTime.now());
                usuarioRepository.save(usuario.get());
            }
        }

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

    public boolean desbanir(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        usuarioOpt.get().setBanido(false);
        usuarioRepository.save(usuarioOpt.get());
        return true;
    }

    public boolean logof(int id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        usuarioOpt.get().setAtivo(false);
        usuarioRepository.save(usuarioOpt.get());
        return true;
    }
}
