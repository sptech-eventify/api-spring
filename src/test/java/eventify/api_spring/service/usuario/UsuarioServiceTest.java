package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;
import eventify.api_spring.dto.usuario.UsuarioInfoDto;
import eventify.api_spring.dto.usuario.UsuarioLoginDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;
import eventify.api_spring.factory.usuario.UsuarioCadastrarDTOFactory;
import eventify.api_spring.factory.usuario.UsuarioFactory;
import eventify.api_spring.factory.usuario.UsuarioLoginDTOFactory;
import eventify.api_spring.repository.UsuarioRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

        final UsuarioDevolverDto resposta = usuarioService.cadastrar(requisicao);

        final Usuario usuarioCapture = usuarioArgumentCaptor.getValue();

        assertNotNull(usuarioCapture);
        assertEquals(requisicao.getTipoUsuario(), usuarioCapture.getTipoUsuario());
        assertEquals(requisicao.getCpf(), usuarioCapture.getCpf());
        assertEquals(requisicao.getNome(), usuarioCapture.getNome());
        assertEquals(requisicao.getEmail(), usuarioCapture.getEmail());
        assertEquals(requisicao.getIsBanido(), usuarioCapture.getIsBanido());
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
        assertTrue(usuario.getIsBanido());
    }

    @Test
    void deve_colocar_false_no_usuario_desbanido() {
        final Usuario usuario = UsuarioFactory.usuario();
        usuario.setIsBanido(true);

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));

        usuarioService.desbanir(1);

        assertFalse(usuario.getIsBanido());
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
        assertEquals(novoUsuario.getIsAtivo(), usuarioAtualizado.getIsAtivo());
        assertEquals(novoUsuario.getIsBanido(), usuarioAtualizado.getIsBanido());
    }

    @Test
    void deve_lancar_ResponseStatusException_ao_nao_encontrar_id() {
        final Usuario novoUsuario = UsuarioFactory.usuarioAtualizado();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> usuarioService.atualizar(2, novoUsuario),
                "Usuário não encontrado");
    }

    @Test
    void deve_colocar_false_o_atributo_ativo_ao_deslogar() {
        final Usuario usuario = UsuarioFactory.usuario();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuarioArgumentCaptor.capture())).thenReturn(usuario);

        usuarioService.logof(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertFalse(usuario.getIsAtivo());
    }

    @Test
    void deve_lancar_ResponseStatusException_quando_usuario_esta_banido() {
        final UsuarioLoginDto usuarioLoginDTO = UsuarioLoginDTOFactory.usuarioLoginDto();
        final Usuario usuario = UsuarioFactory.usuario();
        usuario.setIsBanido(true);

        when(usuarioRepository.findByEmail(emailArgumentCaptor.capture())).thenReturn(Optional.of(usuario));
        assertThrows(ResponseStatusException.class,
                () -> usuarioService.autenticar(usuarioLoginDTO),
                "Usuário banido");
    }

    @Test
    void deve_retornar_usuario_autenticado() {
        final Usuario usuario = UsuarioFactory.usuarioNaoAutenticado();
        final UsuarioLoginDto usuarioLoginDto = UsuarioLoginDTOFactory.usuarioLoginDto();
        final Authentication authentication = Mockito.mock(Authentication.class);
        final String token = "jwt_token";

        when(usuarioRepository.findByEmail(usuarioLoginDto.getEmail())).thenReturn(Optional.of(usuario));

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(gerenciadorTokenJwt.generateToken(authentication)).thenReturn(token);

        final UsuarioTokenDto retorno = usuarioService.autenticar(usuarioLoginDto);

        assertNotNull(retorno);
        assertTrue(usuario.getIsAtivo());
        assertFalse(usuario.getIsBanido());
        assertNotNull(usuario.getUltimoLogin());
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void deve_retornar_uma_lista_vazia() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        final List<UsuarioDevolverDto> listaUsuario = usuarioService.listar();
        assertTrue(listaUsuario.isEmpty());
    }

    @Test
    void deve_retornar_uma_lista_com_dois_usuarios() {
        final List<Usuario> listaRetornada = List.of(UsuarioFactory.usuario(), UsuarioFactory.usuario());

        when(usuarioRepository.findAll()).thenReturn(listaRetornada);

        final List<UsuarioDevolverDto> listaUsuario = usuarioService.listar();
        assertEquals(2, listaUsuario.size());
    }

    @Test
    void deve_retornar_um_usuario_existente() {
        final Usuario usuario = UsuarioFactory.usuario();

        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(usuario));

        UsuarioInfoDto usuarioRetornado = usuarioService.exibir(1);

        assertEquals(1, idArgumentCaptor.getValue());
        assertTrue(!Objects.isNull(usuarioRetornado));

        // assertEquals(usuario.getId(), usuarioRetornado.getId());
        // assertEquals(usuario.getNome(), usuarioRetornado.getNome());
        // assertEquals(usuario.getEmail(), usuarioRetornado.get().getEmail());
    }

    @Test
    void deve_nao_retornar_usuario_com_id_nao_encontrado() {
        when(usuarioRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.empty());

        // Optional<Usuario> usuarioRetornado = usuarioService.exibir(2);

        // assertTrue(usuarioRetornado.isEmpty());
    }
}