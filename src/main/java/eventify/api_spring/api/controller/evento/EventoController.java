package eventify.api_spring.api.controller.evento;

import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.dto.evento.EventoCriacaoDto;
import eventify.api_spring.dto.evento.EventoDto;
import eventify.api_spring.dto.evento.EventoProximoDto;
import eventify.api_spring.dto.orcamento.OrcamentoContratanteDto;
import eventify.api_spring.dto.orcamento.OrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoPropDto;
import eventify.api_spring.dto.smartsync.CalendarioDto;
import eventify.api_spring.service.evento.EventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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
@Tag(name="Evento", description="Controller com os endpoints de evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoDto>> exibirTodosEventos() {
        List<EventoDto> eventos = eventoService.exibirTodosEventos();
        
        return ok(eventos);
    }

    @PostMapping
    public ResponseEntity<Void> criarEvento(@RequestBody @Valid EventoCriacaoDto evento) {
        Evento eventoCriado = eventoService.criarEvento(evento);
        URI location = URI.create(String.format("/eventos/%s", eventoCriado.getId() ));

        return created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDto> eventoPorId(@PathVariable Integer id) {
        EventoDto evento = eventoService.eventoPorId(id);

        return ok(evento);
    }

    @GetMapping("/buffet/orcamento/{idBuffet}")
    public ResponseEntity<List<OrcamentoPropDto>> buscarOrcamentosDoBuffet(@PathVariable Integer idBuffet) {
        List<OrcamentoPropDto> lista = eventoService.buscarOrcamentosDoBuffet(idBuffet);

        return ok(lista);
    }

    @GetMapping("/contratante/{idUsuario}/orcamentos")
    public ResponseEntity<List<OrcamentoContratanteDto>> pegarOrcamentos(@PathVariable Integer idUsuario) {
        List<OrcamentoContratanteDto> orcamentos = eventoService.pegarOrcamentos(idUsuario);

        return ok(orcamentos);
    }

    @GetMapping("/info/{idUsuario}")
    public ResponseEntity<List<EventoDto>> listarEventosInfo(@PathVariable Integer idUsuario) {
        List<EventoDto> eventos = eventoService.listarEventosInfo(idUsuario);

        return ok(eventos);
    }

    @GetMapping("/info/orcamentos/{idUsuario}")
    public ResponseEntity<List<EventoDto>> listarOrcamentos(@PathVariable Integer idUsuario) {
        List<EventoDto> eventos = eventoService.listarOrcamentos(idUsuario);

        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/orcamento")
    public ResponseEntity<OrcamentoDto>buscarOrcamento(@RequestParam Integer id) {
        OrcamentoDto orcamento = eventoService.buscarOrcamento(id);
        
        return ok(orcamento);
    }

    @PutMapping("/orcamento/mandar")
    public ResponseEntity<Void> mandarOrcamento(@RequestParam @Valid Integer id, @RequestParam @Valid Double preco) {
        eventoService.mandarOrcamento(id, preco);
        
        return ok().build();
    }

    @PutMapping("/orcamento/verificar/{id}")
    public ResponseEntity<Integer> verificarPagamento(@PathVariable Integer id) {
        Integer status = eventoService.verificarOrcamento(id);

        return ok(status);
    }

    @PutMapping("/orcamento/pagar/{id}")
    public ResponseEntity<Boolean> pagarOrcamento(@PathVariable Integer id) {
        eventoService.pagarOrcamento(id);

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/proximo-evento/{id}")
    @Transactional
    public ResponseEntity<List<EventoProximoDto>> consultarProximoEvento(@PathVariable Integer id){
        List<EventoProximoDto> logs = eventoService.consultarProximoEvento(id);

        return ok(logs);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/smart-sync/calendario/{id}")
    @Transactional
    public ResponseEntity<List<CalendarioDto>> consultarCalendario(@PathVariable Integer id){
        List<CalendarioDto> datas = eventoService.consultarCalendario(id);

        return ok(datas);
    }
}