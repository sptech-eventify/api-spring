package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    @Captor
    private ArgumentCaptor<Integer> idArgumentCaptor;

    @Test
    void deve_cadastrar_um_usuario_com_os_dados_correto() {
        final UsuarioCadastrarDTO requisicao = usuarioCadastrarDTOFactory();
        final Usuario usuario = usuarioFactory();

        when(usuarioRepository.save(usuarioArgumentCaptor.capture())).thenReturn(usuario);

        final UsuarioDevolverDTO resposta = usuarioService.cadastrar(requisicao);

        final Usuario usuarioCapture = usuarioArgumentCaptor.getValue();

        assertNotNull(usuarioCapture);
        assertEquals(requisicao.getTipoUsuario(), usuarioCapture.getTipoUsuario());
        assertEquals(requisicao.getCpf(), usuarioCapture.getCpf());
        assertEquals(requisicao.getNome(), usuarioCapture.getNome());
        assertEquals(requisicao.getEmail(), usuarioCapture.getEmail());
        assertEquals(requisicao.getBanido(), usuarioCapture.isBanido());
        assertNotEquals(requisicao.getSenha(), usuarioCapture.getSenha());

        assertNotNull(resposta);
        assertEquals(usuarioCapture.getId(), resposta.getId());
        assertEquals(usuarioCapture.getNome(), resposta.getNome());
        assertEquals(usuarioCapture.getEmail(), resposta.getEmail());
    }

    @Test
    void deve_retornar_true_para_id_que_existe(){
        final Usuario usuario = usuarioFactory();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        Boolean resposta = usuarioService.banir(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertTrue(resposta);
    }

    @Test
    void deve_retornar_false_para_id_que_nao_existe(){
        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.empty());
        Boolean resposta = usuarioService.banir(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertFalse(resposta);
    }

    public static UsuarioCadastrarDTO usuarioCadastrarDTOFactory() {
        return new UsuarioCadastrarDTO(
                "Gabriel Santos",
                "gabriel@santos.com",
                "123456",
                "31509983015",
                1
        );
    }

    public static Usuario usuarioFactory() {
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