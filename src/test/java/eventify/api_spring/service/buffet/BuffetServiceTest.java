package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.factory.buffet.BuffetFactory;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.ImagemRepository;
import eventify.api_spring.repository.UsuarioRepository;
import eventify.api_spring.service.BuffetService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
    private EntityManager entityManager;
    @InjectMocks
    private BuffetService service;

    @Test
    void deve_retornar_uma_lista_vazia(){

        when(buffetRepository.findAll()).thenReturn(Collections.emptyList());
        final List<BuffetDtoResposta> lista = service.listar();

        assertTrue(lista.isEmpty());
    }

    @Test
    void deve_retornar_dois_buffets(){
        final Buffet buffet = BuffetFactory.buffet();

        when(buffetRepository.findAll()).thenReturn(List.of(buffet, buffet));
        final List<BuffetDtoResposta> listar = service.listar();

        assertEquals(2, listar.size());
    }
}
