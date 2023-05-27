package eventify.api_spring.service.buffet;

import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.ImagemRepository;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.BuffetService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BuffetServiceTest {
    @Mock
    private BuffetRepository buffetRepository;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private ImagemRepository imagemRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private BuffetService service;



}
