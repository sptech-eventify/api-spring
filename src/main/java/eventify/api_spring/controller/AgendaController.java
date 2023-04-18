package eventify.api_spring.controller;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.repository.AgendaRepository;
import eventify.api_spring.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<Agenda>> exibirAgendas() {
        List<Agenda> agenda = agendaService.exibirAgendas();
        if (agenda.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(agenda);
    }

    @PostMapping
    public ResponseEntity<Agenda> criarAgenda(@RequestBody @Valid Agenda a) {
        agendaService.criarAgenda(a);
        return ResponseEntity.status(201).body(a);
    }
}
