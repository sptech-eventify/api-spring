package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.factory.buffet.BuffetFactory;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.ImagemRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
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
    @Captor
    private ArgumentCaptor<Integer> idArgumentCaptor;
    @Captor
    private ArgumentCaptor<Buffet> buffetArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> parametroArgumentCaptor;

    @Test
    void deve_validar_se_id_passado_eh_o_mesmo_usado_na_pesquisa() {
        when(buffetRepository.findByNomeContainingIgnoreCase(parametroArgumentCaptor.capture())).thenReturn(Collections.emptyList());

        service.getBuffetPorPesquisaNome("Uma pessoa Branda");
        assertEquals("Uma pessoa Branda", parametroArgumentCaptor.getValue());
    }

    @Test
    void deve_retornar_uma_lista_vazia() {

        when(buffetRepository.findAll()).thenReturn(Collections.emptyList());
        final List<BuffetRespostaDto> lista = service.listarBuffets();

        assertTrue(lista.isEmpty());
    }

    @Test
    void deve_retornar_dois_buffets() {
        final Buffet buffet = BuffetFactory.buffet();

        when(buffetRepository.findAll()).thenReturn(List.of(buffet, buffet));
        final List<BuffetRespostaDto> listar = service.listarBuffets();

        assertEquals(2, listar.size());
    }

    // @Test
    // @Disabled("Comportamento inesperado ao rodar o teste diversas vezes")
    // void deve_retornar_dois_tipoEventos() {
    //     final Buffet buffetao = BuffetFactory.buffet();

    //     when(buffetRepository.findAll()).thenReturn(List.of(buffetao));

    //     final List<String> tipoEventos = service.getTipoEventos();

    //     assertEquals(2, tipoEventos.size());
    //     assertEquals("Casamento", tipoEventos.get(0));
    //     assertEquals("Aniversário", tipoEventos.get(1));
    // }

    // @Test
    // void getAvaliacaoEvento_deve_verificar_se_os_dados_permanecem_imutaveis(){
    //     final Buffet buffet = BuffetFactory.buffet();

    //     when(buffetRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(buffet));
    //     when(eventoRepository.findAvaliacaoByBuffet(buffetArgumentCaptor.capture())).thenReturn(anyDouble());

    //     service.getAvaliacaoEvento(1);
    //     assertEquals(1, idArgumentCaptor.getValue());
    //     assertSame(buffet, buffetArgumentCaptor.getValue());
    // }

    @Test
    void deve_retornar_duas_datas_agendadas() {
        final Buffet buffet = BuffetFactory.buffet();

        final List<LocalDate> datas = List.of(buffet.getAgendas().get(0).getData().toLocalDate(), buffet.getAgendas().get(1).getData().toLocalDate());

        when(buffetRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.of(buffet));
        when(eventoRepository.findAllDataByBuffet(buffetArgumentCaptor.capture())).thenReturn(datas);

        List<DataDto> dataDtos = service.pegarDatasOcupadas(1);

        assertFalse(dataDtos.isEmpty());
        assertEquals(1, idArgumentCaptor.getValue());
        assertSame(buffet, buffetArgumentCaptor.getValue());

        assertEquals(2, dataDtos.size());
        assertEquals(2022, dataDtos.get(0).getAno());
        assertEquals(11, dataDtos.get(0).getMes());
        assertEquals(25, dataDtos.get(0).getDia());

        assertEquals(2023, dataDtos.get(1).getAno());
        assertEquals(8, dataDtos.get(1).getMes());
        assertEquals(13, dataDtos.get(1).getDia());
    }

    @Test
    void pegarTaxaDeAbandono_deve_verificar_se_os_dados_permanecem_imutaveis(){
        when(buffetRepository.findById(idArgumentCaptor.capture())).thenReturn(Optional.empty());

        assertNull(service.pegarTaxaDeAbandono(1));
        assertEquals(1, idArgumentCaptor.getValue());
    }
}
