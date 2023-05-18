package eventify.api_spring.service.usuario;

import eventify.api_spring.api.configuration.security.jwt.GerenciadorTokenJwt;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDTO;
import eventify.api_spring.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Mock
    private UsuarioService usuarioService;

    @Disabled("Ainda em construção")
    @Test
    public void deve_cadastar_um_usuario(){
        final UsuarioCadastrarDTO requisicao = usuarioCadastrarDTOFactory();

        when(passwordEncoder.encode());

        final UsuarioDevolverDTO resposta = usuarioService.cadastrar(requisicao);
        assertEquals(requisicao.getNome(), resposta.getNome());
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

}