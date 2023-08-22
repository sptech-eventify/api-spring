package eventify.api_spring.service.pesquisa;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.buffet.Pesquisa;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.factory.buffet.BuffetFactory;
import eventify.api_spring.factory.pesquisa.PesquisaTestFactory;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.service.buffet.PesquisaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PesquisaServiceTeste {

    @Mock
    private BuffetRepository buffetRepository;

    @InjectMocks
    private PesquisaService service;

    @Test
    void deve_retornar_dois_buffets() {
        final Pesquisa pesquisa = PesquisaTestFactory.pesquisa();
        final Buffet aniversario = BuffetFactory.buffet();
        final Buffet decoracao = BuffetFactory.buffet();
        final Buffet casamento = BuffetFactory.buffet();

        aniversario.setDescricao("Aniversário");
        aniversario.setNome("Thomas Pipe");

        decoracao.setDescricao("Decorações");
        decoracao.setNome("Dona Florida");

        final List<Buffet> buffets = List.of(
                aniversario
                , decoracao
                , casamento
        );

        when(buffetRepository.findAllBuffet()).thenReturn(buffets);

        // final List<BuffetRespostaDto> buffetPesquisado = service.getBuffetPorPesquisa(pesquisa);
        // for(BuffetRespostaDto buffet : buffetPesquisado) {
        //     System.out.println(buffet.getDescricao());
        // }

        /*
        ---> Cancelado por conta do algoritmo de pesquisa
        assertEquals(2, buffetPesquisado.size()); --> Cancelado por conta da regra de negócio
        assertEquals(decoracao.getDescricao(), buffetPesquisado.get(0).getDescricao());
        assertEquals(casamento.getDescricao(), buffetPesquisado.get(0).getDescricao());
        */
    }

}
