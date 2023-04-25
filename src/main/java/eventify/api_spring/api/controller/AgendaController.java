package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.service.AgendaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="5. Agenda", description="Controller com os endpoints de reservas dos buffets")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

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
