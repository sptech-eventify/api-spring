package eventify.api_spring.api.controller.evento;

import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.dto.evento.EventoCriacaoDto;
import eventify.api_spring.dto.evento.EventoDto;
import eventify.api_spring.dto.orcamento.OrcamentoDto;
import eventify.api_spring.dto.orcamento.OrcamentoPropDto;
import eventify.api_spring.service.evento.EventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<List<Evento>> exibirTodosEventos() {
        List<Evento> eventos = eventoService.exibirTodosEventos();

        if (eventos.isEmpty()) {
            return notFound().build();
        }
        
        return ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Boolean> criarEvento(@RequestBody @Valid EventoCriacaoDto evento) {
        if (eventoService.criarEvento(evento)) {
            return ok().build();
        }

        return badRequest().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/{nome}")
    public ResponseEntity<Evento> exibirEvento(@PathVariable String nome) {
        Evento evento = eventoService.exibeEvento(nome);

        if (Objects.isNull(evento)) {
            return notFound().build();
        }

        return ok(evento);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/buffet/orcamento/{idBuffet}")
    public ResponseEntity<List<OrcamentoPropDto>> buscarOrcamentosDoBuffet(@PathVariable int idBuffet) {
        List<OrcamentoPropDto> lista = eventoService.buscarOrcamentosDoBuffet(idBuffet);

        if (lista.isEmpty()) {
            return noContent().build();
        }

        return ok(lista);
    }


    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/contratante/{idUser}/orcamentos")
    public ResponseEntity<List<Object[]>> pegarOrcamentos(@PathVariable int idUser) {
        List<Object[]> orcamentos = eventoService.pegarOrcamentos(idUser);

        if (orcamentos.isEmpty()) {
            return noContent().build();
        }

        return ok(orcamentos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/info/{idUser}")
    public ResponseEntity<List<EventoDto>> listarEventosInfo(@PathVariable int idUser) {
        List<EventoDto> eventos = eventoService.listarEventosInfo(idUser);

        if (eventos.isEmpty()) {
            return noContent().build();
        }

        return ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/info/orcamentos/{idUser}")
    public ResponseEntity<List<EventoDto>> listarOrcamentos(@PathVariable int idUser) {
        List<EventoDto> eventos = eventoService.listarOrcamentos(idUser);

        if (eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(eventos);
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamento")
    public ResponseEntity<OrcamentoDto>buscarOrcamento(@RequestParam int idEvento) {
        OrcamentoDto orcamento = eventoService.buscarOrcamento(idEvento);

        if (Objects.isNull(orcamento)) {
            return noContent().build();
        }
        
        return ok(eventoService.buscarOrcamento(idEvento));
    }

    @SecurityRequirement(name = "requiredAuth")
    @PutMapping("/orcamento/mandar")
    public ResponseEntity<Boolean> mandarOrcamento(@RequestParam @Valid int idEvento, @RequestParam @Valid double preco) {
        if (eventoService.mandarOrcamento(idEvento, preco)) {
            return ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/orcamento/verificar/{idEvento}")
    public ResponseEntity<Integer> verificarPagamento(@PathVariable int idEvento) {
        Integer status = eventoService.verificarOrcamento(idEvento);

        if (Objects.isNull(status)) {
            return noContent().build();
        }

        return ok().build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping("/orcamento/pagar/{idEvento}")
    public ResponseEntity<Boolean> pagarOrcamento(@PathVariable int idEvento) {
        if(eventoService.pagarOrcamento(idEvento)){
            return ok().build();
        }

        return badRequest().build();
    }

}
