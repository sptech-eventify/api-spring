package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.smartsync.File;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.buffet.BuffetSmartSyncResumoDto;
import eventify.api_spring.dto.dashboard.AvaliacaoDto;
import eventify.api_spring.dto.dashboard.DadosFinanceiroDto;
import eventify.api_spring.dto.dashboard.MovimentacaoFinanceiraDto;
import eventify.api_spring.dto.dashboard.TaxaAbandonoDto;
import eventify.api_spring.dto.dashboard.TaxaSatisfacaoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.smartsync.AtividadeDto;
import eventify.api_spring.dto.smartsync.AvaliacaoBaseadoEvento;
import eventify.api_spring.dto.smartsync.ImpressaoDto;
import eventify.api_spring.dto.smartsync.InfoEventoDto;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.service.buffet.BuffetService;
import eventify.api_spring.service.smartsync.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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
@Tag(name="Buffet", description="Controller com os endpoints de buffet")
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @Autowired
    private FileService fileService;

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
    @PutMapping("/{id}")
    public ResponseEntity<Buffet> atualizarBuffet(@PathVariable Integer id, @RequestBody @Valid Buffet buffet) {
        Buffet buffetAtualizado = buffetService.atualizarBuffet(id, buffet);

        return ok(buffetAtualizado);
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Buffet> deletarBuffet(@PathVariable Integer id) {
        buffetService.deletarBuffet(id);

        return noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuffetRespostaDto> buscarBuffetPorId(@PathVariable Integer id) {
        BuffetRespostaDto buffet = buffetService.buscarBuffetPublicoPorIdResposta(id);
        
        return ok(buffet);
    }

    @GetMapping("/{id}/avaliacao")
    public ResponseEntity<Double> avaliacaoBuffet(@PathVariable Integer id) {
        Double avaliacao = buffetService.avaliacaoBuffet(id);
    
        return ok(avaliacao);
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<List<ImagemDto>> caminhoImagemBuffet(@PathVariable Integer id) {
        List<ImagemDto> imagens = buffetService.caminhoImagemBuffet(id);

        return ok(imagens);
    }

    @GetMapping("/publico/{id}")
    public ResponseEntity<BuffetPublicoDto> buscarBuffetPublico(@PathVariable Integer id) {
        BuffetPublicoDto buffet = buffetService.buscarBuffetPublico(id);

        return ok(buffet);
    }

    @GetMapping("/publico/listar")
    public ResponseEntity<Map<String, List<BuffetResumoDto>>> listarBuffetsResumidosPublico() {
        Map<String, List<BuffetResumoDto>> buffets = buffetService.listarBuffetsResumidosPublico();
        
        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/datas/{id}")
    public ResponseEntity<List<DataDto>> datasOcupadas(@PathVariable Integer id) {
        List<DataDto> datas = buffetService.datasOcupadas(id);

        return ok(datas);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamentos/{id}")
    public ResponseEntity<List<EventoOrcamentoDto>> orcamentosPorId(@PathVariable Integer id) {
        List<EventoOrcamentoDto> orcamentos = buffetService.orcamentosPorIdBuffet(id);
        
        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/proprietario/{id}")
    public ResponseEntity<List<BuffetResumoDto>> buffetsPorIdUsuario(@PathVariable Integer id) {
        List<BuffetResumoDto> buffets = buffetService.buffetsPorIdUsuario(id);

        return ok(buffets);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/abandono/{id}")
    public ResponseEntity<TaxaAbandonoDto> taxaAbandono(@PathVariable Integer id) {
        TaxaAbandonoDto taxaAbandono = buffetService.taxaAbandono(id);
       
        return ok(taxaAbandono);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/satisfacao/{id}")
    public ResponseEntity<TaxaSatisfacaoDto> taxaSatisfacao(@PathVariable Integer id) {
        TaxaSatisfacaoDto taxaSatisfacao = buffetService.taxaSatisfacao(id);

        return ok(taxaSatisfacao);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/financeiro/{id}")
    public ResponseEntity<MovimentacaoFinanceiraDto> movimentacaoFinanceira(@PathVariable Integer id) {
        MovimentacaoFinanceiraDto movimentacaoFinanceira = buffetService.movimentacaoFinanceira(id);
       
        return ok(movimentacaoFinanceira);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/dados-financeiro/{id}")
    public ResponseEntity<List<DadosFinanceiroDto>> dadosFinanceiro(@PathVariable Integer id) {
        List<DadosFinanceiroDto> dadosFinanceiro = buffetService.dadosFinanceiro(id);

        return ok(dadosFinanceiro);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/dashboard/avaliacoes/{id}")
    public ResponseEntity<List<AvaliacaoDto>> avaliacoes(@PathVariable Integer id) {
        List<AvaliacaoDto> avaliacoes = buffetService.avaliacoes(id);

        return ok(avaliacoes);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/{id}")
    public ResponseEntity<BuffetSmartSyncResumoDto> resumoBuffet(@PathVariable Integer id){
        BuffetSmartSyncResumoDto buffet = buffetService.resumoBuffet(id);

        return ok(buffet);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/transacoes/{id}")
    public ResponseEntity<List<File>> consultarTransacoes(@PathVariable Integer id){
        List<File> log = fileService.consultarTransacoes(id);

        return ok(log);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/atividades/{id}")
    @Transactional
    public ResponseEntity<List<AtividadeDto>> consultarAtividades(@PathVariable Integer id){
        List<AtividadeDto> logs = buffetService.consultarAtividades(id);

        return ok(logs);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/impressoes/{id}")
    @Transactional
    public ResponseEntity<ImpressaoDto> consultarImpressoes(@PathVariable Integer id){
        ImpressaoDto impressoes = buffetService.consultarImpressoes(id);

        return ok(impressoes);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/avaliacao-baseada-eventos/{id}")
    @Transactional
    public ResponseEntity<AvaliacaoBaseadoEvento> consultarAvaliacaoBaseadoEventos(@PathVariable Integer id){
        AvaliacaoBaseadoEvento avaliacao = buffetService.consultarAvaliacaoBaseadoEventos(id);

        return ok(avaliacao);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/info-eventos/{idBuffet}")
    @Transactional
    public ResponseEntity<List<InfoEventoDto>> consultarInfoEventos(@PathVariable Integer idBuffet){
        List<InfoEventoDto> infos = buffetService.consultarInfoEventos(idBuffet);

        return ok(infos);
    }

}