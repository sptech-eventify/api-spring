package eventify.sptech.apispring.Controllers;

import eventify.sptech.apispring.Entities.Buffet;
import eventify.sptech.apispring.Repositories.BuffetRepository;
import eventify.sptech.apispring.Repositories.EventoRepository;
import eventify.sptech.apispring.Services.BuffetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = "http://localhost:3000")
public class BuffetController {

    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private BuffetService buffetService;


    @GetMapping
    public ResponseEntity<List<Buffet>> listar() {
        return ResponseEntity.status(200).body(buffetRepository.findAll());
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> listarTipoEventos() {
        List<String> tipos = buffetService.getTipoEventos();
        if (tipos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(tipos);
    }

    @GetMapping("/{idBuffet}/avaliacao")
    public ResponseEntity<Double> listarAvaliacoesEvento(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.getAvaliacaoEvento(idBuffet));
    }

}
