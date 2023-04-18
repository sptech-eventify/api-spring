package eventify.api_spring.controller;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.repository.AgendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepository;

    @GetMapping
    public ResponseEntity<List<Agenda>> exibirAgendas() {
        List<Agenda> agenda = this.agendaRepository.findAll();
        return ResponseEntity.status(200).body(agenda);
    }

    @PostMapping
    public ResponseEntity<Agenda> criarAgenda(@RequestBody @Valid Agenda a) {
        Agenda agenda = this.agendaRepository.save(a);
        return ResponseEntity.status(201).body(a);
    }
}
