package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Evento;
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

}
