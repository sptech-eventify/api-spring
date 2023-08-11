package eventify.api_spring.api.controller.evento;

import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.dto.evento.EventoCriacaoDto;
import eventify.api_spring.dto.evento.EventoDto;
import eventify.api_spring.dto.orcamento.OrcamentoContratanteDto;
import eventify.api_spring.dto.orcamento.OrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoPropDto;
import eventify.api_spring.service.evento.EventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="4. Evento", description="Controller com os endpoints de evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoDto>> exibirTodosEventos() {
        List<EventoDto> eventos = eventoService.exibirTodosEventos();
        
        return ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Void> criarEvento(@RequestBody @Valid EventoCriacaoDto evento) {
        Evento eventoCriado = eventoService.criarEvento(evento);
        URI location = URI.create(String.format("/eventos/%s", eventoCriado.getId() ));

        return created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDto> eventoPorId(@PathVariable Integer idEvento) {
        EventoDto evento = eventoService.eventoPorId(idEvento);

        return ok(evento);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/buffet/orcamento/{idBuffet}")
    public ResponseEntity<List<OrcamentoPropDto>> buscarOrcamentosDoBuffet(@PathVariable Integer idBuffet) {
        List<OrcamentoPropDto> lista = eventoService.buscarOrcamentosDoBuffet(idBuffet);

        return ok(lista);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/contratante/{idUsuario}/orcamentos")
    public ResponseEntity<List<OrcamentoContratanteDto>> pegarOrcamentos(@PathVariable Integer idUsuario) {
        List<OrcamentoContratanteDto> orcamentos = eventoService.pegarOrcamentos(idUsuario);

        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/info/{idUsuario}")
    public ResponseEntity<List<EventoDto>> listarEventosInfo(@PathVariable Integer idUsuario) {
        List<EventoDto> eventos = eventoService.listarEventosInfo(idUsuario);

        return ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/info/orcamentos/{idUsuario}")
    public ResponseEntity<List<EventoDto>> listarOrcamentos(@PathVariable Integer idUsuario) {
        List<EventoDto> eventos = eventoService.listarOrcamentos(idUsuario);

        return ResponseEntity.ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamento")
    public ResponseEntity<OrcamentoDto>buscarOrcamento(@RequestParam Integer idEvento) {
        OrcamentoDto orcamento = eventoService.buscarOrcamento(idEvento);
        
        return ok(orcamento);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/orcamento/mandar")
    public ResponseEntity<Void> mandarOrcamento(@RequestParam @Valid Integer idEvento, @RequestParam @Valid Double preco) {
        eventoService.mandarOrcamento(idEvento, preco);
        
        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/orcamento/verificar/{idEvento}")
    public ResponseEntity<Integer> verificarPagamento(@PathVariable Integer idEvento) {
        Integer status = eventoService.verificarOrcamento(idEvento);

        return ok(status);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/orcamento/pagar/{idEvento}")
    public ResponseEntity<Boolean> pagarOrcamento(@PathVariable Integer idEvento) {
        eventoService.pagarOrcamento(idEvento);

        return ok().build();
    }
}