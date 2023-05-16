package eventify.api_spring.api.controller;

import eventify.api_spring.api.assets.ListaBuffet;
import eventify.api_spring.domain.*;
import eventify.api_spring.dto.usuario.BuffetDto;
import eventify.api_spring.service.BuffetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hibernate.Hibernate.get;

@RestController
@RequestMapping("/pesquisa")
@Tag(name="3. Pesquisa", description="Controller com os endpoints que controlam as pesquisas do sistema")
public class PesquisaController {
    @Autowired
    private BuffetService buffetService;

    @GetMapping
    private ResponseEntity<List<BuffetDto>> buscarBuffetProximo(@RequestBody Pesquisa p) {
        List<Buffet> buffets = buffetService.listar();
        List<Buffet> buffetsFiltrados = new ArrayList<>();
        List<BuffetDto> buffetDtos = new ArrayList<>();

        for (int i = 0; i < buffets.size(); i++) {
            boolean isDentro = true;
            boolean isTipoEvento = false;
            boolean isFaixaEtaria = false;
            boolean isServico = false;

            for(TipoEvento tp: buffets.get(i).getTiposEventos()){
                if(tp.getDescricao().equals(p.getTipoEvento())){
                    isTipoEvento = true;
                }
            }

            for(FaixaEtaria fe: buffets.get(i).getFaixaEtarias()){
                if(fe.getDescricao().equals(p.getFaixaEtaria())){
                    isFaixaEtaria = true;
                }
            }

            for(Servico se: buffets.get(i).getServicos()){
                if(se.getDescricao().equals(p.getServico())){
                    isServico = true;
                }
            }

            if (calcularDistancia(p.getLatitude(), p.getLongitude(), buffets.get(i).getEndereco().getLatitude(), buffets.get(i).getEndereco().getLongitude()) > 15) {
                isDentro = false;
            }

            if (isDentro && (isTipoEvento && isFaixaEtaria && isServico)) {
                buffetsFiltrados.add(buffets.get(i));
            }
        }

        if((buffetsFiltrados.size()) == 0){
            return ResponseEntity.notFound().build();
        }

        for (Buffet b: buffetsFiltrados) {
            buffetDtos.add(new BuffetDto(
                    b.getId(),
                    b.getNome(),
                    b.getDescricao(),
                    b.getTamanho(),
                    b.getPrecoMedioDiaria(),
                    b.getQtdPessoas(),
                    b.getCaminhoComprovante(),
                    b.isVisivel(),
                    b.getEndereco(),
                    b.getFaixaEtarias(),
                    b.getTiposEventos(),
                    b.getServicos(),
                    b.getImagemDto(),
                    b.getAgendas()
            ));

        }
        return ResponseEntity.ok().body(buffetDtos);
    }

    /*@GetMapping("/ordem-alfabetica")
    private ResponseEntity<List<BuffetDto>> ordenarAlfabeticamente() {
        List<Buffet> buffets = buffetService.listar();
        ListaBuffet buffetsOrdenados = new ListaBuffet(buffets.size());
        List<BuffetDto> buffetDtos = new ArrayList<>();

        for(Buffet b: buffets){
            buffetsOrdenados.adiciona(b);
        }

        buffetsOrdenados.ordenaPorNome();

        if((buffetsOrdenados.tamanho()) == 0){
            return ResponseEntity.notFound().build();
        }

        for(int i = 0; i < buffetsOrdenados.tamanho(); i++){
            buffetDtos.add(new BuffetDto(
                    buffetsOrdenados.get(i).getId(),
                    buffetsOrdenados.get(i).getNome(),
                    buffetsOrdenados.get(i).getDescricao(),
                    buffetsOrdenados.get(i).getTamanho(),
                    buffetsOrdenados.get(i).getPrecoMedioDiaria(),
                    buffetsOrdenados.get(i).getQtdPessoas(),
                    buffetsOrdenados.get(i).getCaminhoComprovante(),
                    buffetsOrdenados.get(i).isVisivel(),
                    buffetsOrdenados.get(i).getEndereco(),
                    buffetsOrdenados.get(i).getFaixaEtarias(),
                    buffetsOrdenados.get(i).getTiposEventos(),
                    buffetsOrdenados.get(i).getServicos(),
                    buffetsOrdenados.get(i).getImagemDto(),
                    buffetsOrdenados.get(i).getAgendas()
            ));
        }

        return ResponseEntity.ok().body(buffetDtos);
    }*/

    public static Double calcularDistancia(Double lat1, Double long1, Double lat2, Double long2) {
        // raio médio da Terra em quilômetros
        double raioTerra = 6371;

        // Calcula a diferença de latitude e longitude entre os dois pontos e converte o resultado para radianos.
        Double difLat = Math.toRadians(lat2 - lat1);
        Double difLong = Math.toRadians(long2 - long1);

        // Esta linha calcula um valor intermediário chamado a usando a fórmula de Haversine
        Double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(difLong / 2) * Math.sin(difLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distancia = raioTerra * c;

        return distancia;
    }
}
