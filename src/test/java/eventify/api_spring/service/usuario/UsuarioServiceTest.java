package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.factory.usuario.UsuarioCadastrarDTOFactory;
import eventify.api_spring.factory.usuario.UsuarioFactory;
import eventify.api_spring.factory.usuario.UsuarioLoginDTOFactory;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.usuario.dto.UsuarioLoginDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    @Captor
    private ArgumentCaptor<String> emailArgumentCaptor;

    @Test
    void deve_cadastrar_um_usuario_com_os_dados_correto() {
        final UsuarioCadastrarDTO requisicao = UsuarioCadastrarDTOFactory.usuarioCadastrarDTO();
        final Usuario usuario = UsuarioFactory.usuario();

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
    void deve_retornar_true_para_id_que_existe() {
        final Usuario usuario = UsuarioFactory.usuario();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        Boolean resposta = usuarioService.banir(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertTrue(resposta);
    }

    @Test
    void deve_retornar_false_para_id_que_nao_existe() {
        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.empty());
        Boolean resposta = usuarioService.banir(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertFalse(resposta);
    }

    @Test
    void deve_colocar_true_no_usuario_banido() {
        final Usuario usuario = UsuarioFactory.usuario();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        usuarioService.banir(1);

        assertEquals(1, idArgumentCaptor.getValue());

        verify(usuarioRepository, times(0)).deleteById(anyInt());
        assertTrue(usuario.isBanido());
    }

    @Test
    void deve_colocar_false_no_usuario_desbanido() {
        final Usuario usuario = UsuarioFactory.usuario();
        usuario.setBanido(true);

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));

        usuarioService.desbanir(1);

        assertFalse(usuario.isBanido());
        assertEquals(1, idArgumentCaptor.getValue());
    }

    @Test
    void deve_atualizar_os_dados_corretamente() {

        final Usuario usuario = UsuarioFactory.usuario();
        final Usuario novoUsuario = UsuarioFactory.usuarioAtualizado();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(novoUsuario);

        final UsuarioCadastrarDTO usuarioAtualizado = usuarioService.atualizar(1, novoUsuario);

        assertEquals(1, idArgumentCaptor.getValue());

        assertEquals(idArgumentCaptor.getValue(), usuario.getId());
        assertEquals(usuario.getId(), novoUsuario.getId());

        assertEquals(novoUsuario.getNome(), usuarioAtualizado.getNome());
        assertEquals(novoUsuario.getCpf(), usuarioAtualizado.getCpf());
        assertEquals(novoUsuario.getEmail(), usuarioAtualizado.getEmail());
        assertNotEquals(novoUsuario.getSenha(), usuarioAtualizado.getSenha());
        assertEquals(novoUsuario.getTipoUsuario(), usuarioAtualizado.getTipoUsuario());
        assertEquals(novoUsuario.isAtivo(), usuarioAtualizado.getAtivo());
        assertEquals(novoUsuario.isBanido(), usuarioAtualizado.getBanido());
    }

    @Test
    void deve_lancar_ResponseStatusException_ao_nao_encontrar_id() {
        final Usuario novoUsuario = UsuarioFactory.usuarioAtualizado();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.atualizar(2, novoUsuario));
    }

    @Test
    void deve_colocar_false_o_atributo_ativo_ao_deslogar(){
        final Usuario usuario = UsuarioFactory.usuario();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuarioArgumentCaptor.capture())).thenReturn(usuario);

        usuarioService.logof(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertFalse(usuario.isAtivo());
    }

}