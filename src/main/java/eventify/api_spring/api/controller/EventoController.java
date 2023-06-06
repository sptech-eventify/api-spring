package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Evento;
import eventify.api_spring.dto.EventoDto;
import eventify.api_spring.dto.OrcamentoDto;
import eventify.api_spring.dto.OrcamentoPropDto;
import eventify.api_spring.service.EventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Optional;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="4. Evento", description="Controller com os endpoints de evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody @Valid Evento e) {
        return ResponseEntity.status(201).body(eventoService.criarEvento(e));
    }

    @GetMapping
    public ResponseEntity<List<Evento>> exibirTodosEventos() {
        List<Evento> eventos = eventoService.exibirTodosEventos();
        if (eventos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(eventos);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Evento> exibirEvento(@PathVariable String nome) {
        return ResponseEntity.of(eventoService.exibeEvento(nome));
    }

    @GetMapping("/contratante/{idUser}/orcamentos")
    public ResponseEntity<List<Object[]>> pegarOrcamentos(@PathVariable int idUser) {
        List<Object[]> result = eventoService.pegarOrcamentos(idUser);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/info/{idUser}")
    public ResponseEntity<List<EventoDto>> listarEventosInfo(@PathVariable int idUser) {
        List<EventoDto> lista = eventoService.listarEventosInfo(idUser);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/info/orcamentos/{idUser}")
    public ResponseEntity<List<EventoDto>> listarOrcamentos(@PathVariable int idUser) {
        List<EventoDto> lista = eventoService.listarOrcamentos(idUser);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/orcamento")
    public ResponseEntity<OrcamentoDto>buscarOrcamento(@RequestParam int idEvento) {
        return ResponseEntity.ok(eventoService.buscarOrcamento(idEvento));
    }

    @GetMapping("/buffet/orcamento/{idBuffet}")
    public ResponseEntity<List<OrcamentoPropDto>> buscarOrcamentosDoBuffet(@PathVariable int idBuffet) {
        List<OrcamentoPropDto> lista = eventoService.buscarOrcamentosDoBuffet(idBuffet);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/orcamento/mandar")
    public ResponseEntity<Boolean> mandarOrcamento(@RequestParam @Valid int idEvento, @RequestParam @Valid double preco) {
        if (eventoService.mandarOrcamento(idEvento, preco)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/orcamento/verificar/{idEvento}")
    public ResponseEntity<Integer> verificarPagamento(@PathVariable int idEvento) {
        return ResponseEntity.ok(eventoService.verificarOrcamento(idEvento));
    }

    @PostMapping("/orcamento/pagar/{idEvento}")
    public ResponseEntity<Boolean> pagarOrcamento(@PathVariable int idEvento) {
        if (eventoService.pagarOrcamento(idEvento)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
