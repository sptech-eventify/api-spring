package eventify.api_spring.api.controller.pesquisa;

import eventify.api_spring.domain.buffet.Pesquisa;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.service.buffet.PesquisaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.time.LocalDate;
import java.util.List;

// Finalizada

@RestController
@RequestMapping("/pesquisa")
@Tag(name="Pesquisa", description="Controller com os endpoints de pesquisas do sistema")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
public class PesquisaController {
    @Autowired
    private PesquisaService pesquisaService;

    @GetMapping
    private ResponseEntity<List<BuffetRespostaDto>> buscador(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "faixaEtaria", required = false) List<String> faixaEtaria,
            @RequestParam(value = "tamanho", required = false) Integer tamanho,
            @RequestParam(value = "qtdPessoas", required = false) Integer qtdPessoas,
            @RequestParam(value = "tipoEvento", required = false) List<String> tipoEvento,
            @RequestParam(value = "orcMin", required = false) Double orcMin,
            @RequestParam(value = "orcMax", required = false) Double orcMax,
            @RequestParam(value = "dataEvento", required = false) LocalDate dataEvento,
            @RequestParam(value = "servico", required = false) List<String> servico,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude) {

        if(nome == null
                && faixaEtaria == null
                && tamanho == null
                && qtdPessoas == null
                && tipoEvento == null
                && orcMin == null
                && orcMax == null
                && dataEvento == null
                && servico == null
                && latitude == null
                && longitude == null){
            List<BuffetRespostaDto> lista = pesquisaService.getTodosBuffets();

            if(lista.isEmpty()){
                return notFound().build();
            }

            return ok(lista);
        }

        String nomeTratado = nome != null ? nome : "";

        Pesquisa p = new Pesquisa(nomeTratado, faixaEtaria, tamanho, qtdPessoas, tipoEvento, orcMin, orcMax, dataEvento, servico, latitude, longitude);
        List<BuffetRespostaDto> listaFiltrada = pesquisaService.getBuffetPorPesquisa(p);

        if((listaFiltrada.size()) == 0){
            return notFound().build();
        }

        return ok(listaFiltrada);
    }

    @GetMapping("/notas")
    public ResponseEntity<List<Object>> getNotas() {
        List<Object> lista = pesquisaService.getNotas();

        if (lista.isEmpty()){
            return notFound().build();
        }

        return ok(lista);
    }
}
