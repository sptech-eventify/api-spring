package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.service.buffet.BuffetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="2. Buffet", description="Controller com os endpoints de buffet")
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<BuffetRespostaDto>> listarBuffets() {
        List<BuffetRespostaDto> buffets = buffetService.listarBuffets();

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Buffet> criarBuffet(@RequestBody @Valid Buffet buffet) {
        Buffet buffetSalvo = buffetService.criarBuffet(buffet);
        URI location = URI.create("/api/agendas/" + buffetSalvo.getId());
        
        return created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/{idBuffet}")
    public ResponseEntity<Buffet> atualizarBuffet(@PathVariable Integer idBuffet, @RequestBody @Valid Buffet buffet) {
        Buffet buffetAtualizado = buffetService.atualizarBuffet(idBuffet, buffet);

        return ok(buffetAtualizado);
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/{idBuffet}")
    public ResponseEntity<Buffet> deletarBuffet(@PathVariable Integer idBuffet) {
        buffetService.deletarBuffet(idBuffet);

        return noContent().build();
    }

    @GetMapping("/{idBuffet}")
    public ResponseEntity<BuffetRespostaDto> buscarBuffetPorId(@PathVariable Integer idBuffet) {
        BuffetRespostaDto buffet = buffetService.buscarBuffetPublicoPorIdResposta(idBuffet);
        
        return ok(buffet);
    }

    @GetMapping("/{idBuffet}/avaliacao")
    public ResponseEntity<Double> avaliacaoBuffet(@PathVariable Integer idBuffet) {
        Double avaliacao = buffetService.avaliacaoBuffet(idBuffet);
    
        return ok(avaliacao);
    }

    @GetMapping("/{idBuffet}/imagem")
    public ResponseEntity<List<ImagemDto>> caminhoImagemBuffet(@PathVariable Integer idBuffet) {
        List<ImagemDto> imagens = buffetService.caminhoImagemBuffet(idBuffet);

        return ok(imagens);
    }

    @GetMapping("/publico/{idBuffet}")
    public ResponseEntity<BuffetPublicoDto> buscarBuffetPublico(@PathVariable Integer idBuffet) {
        BuffetPublicoDto buffet = buffetService.buscarBuffetPublico(idBuffet);

        return ok(buffet);
    }

    @GetMapping("/publico/listar")
    public ResponseEntity<Map<String, List<BuffetResumoDto>>> listarBuffetsResumidosPublico() {
        Map<String, List<BuffetResumoDto>> buffets = buffetService.listarBuffetsResumidosPublico();
        
        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/datas/{idBuffet}")
    public ResponseEntity<List<DataDto>> pegarDatasOcupadas(@PathVariable int idBuffet) {
        List<DataDto> datas = buffetService.pegarDatasOcupadas(idBuffet);

        if (datas.isEmpty()) {
            return noContent().build();
        } else if (Objects.isNull(datas)) {
            return notFound().build();
        }

        return ok(datas);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamentos/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarOrcamentos(@PathVariable int idBuffet) {
        List<Object[]> orcamentos = buffetService.pegarOrcamentos(idBuffet);
        
        if (Objects.isNull(orcamentos)) {
            return notFound().build();
        } else if (orcamentos.isEmpty()) {
            return noContent().build();
        }

        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/proprietario/{idUser}")
    public ResponseEntity<List<BuffetResumoDto>> pegarBuffetsProprietario(@PathVariable int idUser) {
        List<BuffetResumoDto> buffets = buffetService.pegarBuffetsProprietario(idUser);
        
        if (Objects.isNull(buffets)) {
            return notFound().build();
        } else if (buffets.isEmpty()) {
            return noContent().build();
        }

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/abandono/{idBuffet}")
    public ResponseEntity<List<Long>> pegarTaxaDeAbandono(@PathVariable int idBuffet) {
        List<Long> taxaAbandono = buffetService.pegarTaxaDeAbandono(idBuffet);
        
        if (Objects.isNull(taxaAbandono)) {
            return notFound().build();
        } else if (taxaAbandono.isEmpty()) {
            return noContent().build();
        }

        return ok(taxaAbandono);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/satisfacao/{idBuffet}")
    public ResponseEntity<List<Object>> pegarTaxaDeSatisfacao(@PathVariable int idBuffet) {
        List<Object> taxaSatisfacao = buffetService.pegarTaxaDeSatisfacao(idBuffet);

         if (Objects.isNull(taxaSatisfacao)) {
            return notFound().build();
        } else if (taxaSatisfacao.isEmpty()) {
            return noContent().build();
        }

        return ok(taxaSatisfacao);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/financeiro/{idBuffet}")
    public ResponseEntity<List<Object>> pegarMovimentacaoFinanceira(@PathVariable int idBuffet) {
        List<Object> movimentacaoFinanceira = buffetService.pegarMovimentacaoFinanceira(idBuffet);
       
         if (Objects.isNull(movimentacaoFinanceira)) {
            return notFound().build();
        } else if (movimentacaoFinanceira.isEmpty()) {
            return noContent().build();
        }

        return ResponseEntity.ok(movimentacaoFinanceira);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/dados-financeiro/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarDadosFinanceiro(@PathVariable int idBuffet) {
        List<Object[]> dadosFinanceiro = buffetService.pegarDadosFinanceiro(idBuffet);
        
        if (Objects.isNull(dadosFinanceiro)) {
            return notFound().build();
        } else if (dadosFinanceiro.isEmpty()) {
            return noContent().build();
        }

        return ok(dadosFinanceiro);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/avaliacoes/{idBuffet}")
    public ResponseEntity<List<Object[]>> pegarAvaliacoes(@PathVariable int idBuffet) {
        List<Object[]> avaliacoes = buffetService.pegarAvaliacoes(idBuffet);
        
        if (Objects.isNull(avaliacoes)) {
            return notFound().build();
        } else if (avaliacoes.isEmpty()) {
            return noContent().build();
        }

        return ok(avaliacoes);
    }
}
