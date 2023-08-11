package eventify.api_spring.api.controller.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.dto.agenda.AgendaCriacaoDto;
import eventify.api_spring.dto.agenda.AgendaDto;
import eventify.api_spring.service.agenda.AgendaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendas")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="5. Agenda", description="Controller com os endpoints de reservas dos buffets")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping
    public ResponseEntity<List<AgendaDto>> listarAgendas() {
        List<AgendaDto> agenda = agendaService.listarAgendas();

        return ok(agenda);
    }

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<Agenda> criarAgenda(@RequestBody AgendaCriacaoDto agenda) {
        Agenda agendaSalva = agendaService.criarAgenda(agenda);
        URI location = URI.create("/api/agendas/" + agendaSalva.getId());
        
        return created(location).build();
    }

    @SecurityRequirement(name = "requiredAuth")
    @GetMapping("/{idAgenda}")
    public ResponseEntity<AgendaDto> buscarAgendaPorId(@PathVariable Integer idAgenda) {
        AgendaDto agenda = agendaService.buscarAgendaPorId(idAgenda);

        return ok(agenda);
    }

    @SecurityRequirement(name = "requiredAuth")
    @DeleteMapping("/{idAgenda}")
    public ResponseEntity<Void> deletarAgenda(@PathVariable Integer idAgenda) {
        agendaService.deletarAgenda(idAgenda);

        return noContent().build();
    }
}