package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.dashboard.AvaliacaoDto;
import eventify.api_spring.dto.dashboard.DadosFinanceiroDto;
import eventify.api_spring.dto.dashboard.MovimentacaoFinanceiraDto;
import eventify.api_spring.dto.dashboard.TaxaAbandonoDto;
import eventify.api_spring.dto.dashboard.TaxaSatisfacaoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
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
    public ResponseEntity<List<DataDto>> datasOcupadas(@PathVariable Integer idBuffet) {
        List<DataDto> datas = buffetService.datasOcupadas(idBuffet);

        return ok(datas);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamentos/{idBuffet}")
    public ResponseEntity<List<EventoOrcamentoDto>> orcamentosPorIdBuffet(@PathVariable Integer idBuffet) {
        List<EventoOrcamentoDto> orcamentos = buffetService.orcamentosPorIdBuffet(idBuffet);
        
        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/proprietario/{idUsuario}")
    public ResponseEntity<List<BuffetResumoDto>> buffetsPorIdUsuario(@PathVariable Integer idUsuario) {
        List<BuffetResumoDto> buffets = buffetService.buffetsPorIdUsuario(idUsuario);

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/abandono/{idBuffet}")
    public ResponseEntity<TaxaAbandonoDto> taxaAbandono(@PathVariable Integer idBuffet) {
        TaxaAbandonoDto taxaAbandono = buffetService.taxaAbandono(idBuffet);
       
        return ok(taxaAbandono);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/satisfacao/{idBuffet}")
    public ResponseEntity<TaxaSatisfacaoDto> taxaSatisfacao(@PathVariable Integer idBuffet) {
        TaxaSatisfacaoDto taxaSatisfacao = buffetService.taxaSatisfacao(idBuffet);

        return ok(taxaSatisfacao);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/financeiro/{idBuffet}")
    public ResponseEntity<MovimentacaoFinanceiraDto> movimentacaoFinanceira(@PathVariable Integer idBuffet) {
        MovimentacaoFinanceiraDto movimentacaoFinanceira = buffetService.movimentacaoFinanceira(idBuffet);
       
        return ok(movimentacaoFinanceira);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/dados-financeiro/{idBuffet}")
    public ResponseEntity<List<DadosFinanceiroDto>> dadosFinanceiro(@PathVariable Integer idBuffet) {
        List<DadosFinanceiroDto> dadosFinanceiro = buffetService.dadosFinanceiro(idBuffet);

        return ok(dadosFinanceiro);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/avaliacoes/{idBuffet}")
    public ResponseEntity<List<AvaliacaoDto>> avaliacoes(@PathVariable Integer idBuffet) {
        List<AvaliacaoDto> avaliacoes = buffetService.avaliacoes(idBuffet);

        return ok(avaliacoes);
    }
}
