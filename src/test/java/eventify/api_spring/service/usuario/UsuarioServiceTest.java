package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UsuarioServiceTest {

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    @Disabled("Ainda em construção")
    void deve_cadastrar_um_usuario_com_os_dados_correto(){
        final UsuarioCadastrarDTO requisicao = usuarioCadastrarDTOFactory();
        final Usuario usuario = usuarioFactory();

        when(usuarioRepository.save(usuarioArgumentCaptor.capture())).thenReturn(usuario);

        final UsuarioDevolverDTO resposta = usuarioService.cadastrar(requisicao);

        Usuario capture = usuarioArgumentCaptor.capture();

        assertEquals(requisicao.getTipoUsuario(), capture.getTipoUsuario());
        assertEquals(requisicao.getCpf(), capture.getCpf());
        assertEquals(requisicao.getNome(), capture.getNome());
        assertEquals(requisicao.getEmail(), capture.getEmail());
        assertEquals(requisicao.getAtivo(), capture.isAtivo());
        assertEquals(requisicao.getBanido(), capture.isBanido());
        assertNotEquals(requisicao.getSenha(), capture.getSenha());

        assertEquals(capture.getId(), resposta.getId());
        assertEquals(capture.getNome(), resposta.getNome());
        assertEquals(capture.getEmail(), resposta.getEmail());
    }

    public static UsuarioCadastrarDTO usuarioCadastrarDTOFactory(){
        return new UsuarioCadastrarDTO(
                "Gabriel Santos",
                "gabriel@santos.com",
                "123456",
                "31509983015",
                1
        );
    }

    public static Usuario usuarioFactory(){
        Usuario usuario = new Usuario();

        usuario.setNome("Gabriel Santos");
        usuario.setEmail("gabriel@santos.com");
        usuario.setAtivo(true);
        usuario.setTipoUsuario(1);
        usuario.setSenha("123456");
        usuario.setCpf("31509983015");
        return usuario;
    }

}