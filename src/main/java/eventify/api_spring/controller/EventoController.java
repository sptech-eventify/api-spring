package eventify.api_spring.controller;

import eventify.api_spring.domain.Evento;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;


    @GetMapping
    public ResponseEntity<List<Evento>> exibirTodosEventos() {
        List<Evento> eventos = this.eventoRepository.findAll();
        return ResponseEntity.status(200).body(eventos);
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<Evento> exibirEvento(@PathVariable int idEvento) {
        return ResponseEntity.of(eventoRepository.findById(idEvento));
    }

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody @Valid Evento e) {
        Evento evento = this.eventoRepository.save(e);
        return ResponseEntity.status(201).body(e);
    }
}
