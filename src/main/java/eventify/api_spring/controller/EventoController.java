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

    @GetMapping("/{nome}")
    public ResponseEntity<Evento> exibirEvento(@PathVariable String nome) {
        for (Evento evento: eventoRepository.findAll()) {
            if (evento.getBuffet().getNome().equals(nome)) {
                return ResponseEntity.status(200).body(evento);
            }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/data/{nome}")
    public ResponseEntity<List<LocalDate>> exibirDataBuffet(@PathVariable String nome) {
        List<LocalDate> datas = new ArrayList<>();
        for (Evento evento: eventoRepository.findAll()) {
            if (evento.getBuffet().getNome().equals(nome)) {
                datas.add(evento.getData());
            }
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody @Valid Evento e) {
        Evento evento = this.eventoRepository.save(e);
        return ResponseEntity.status(201).body(e);
    }
}
