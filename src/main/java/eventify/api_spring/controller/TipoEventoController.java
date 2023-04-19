package eventify.api_spring.controller;

import eventify.api_spring.domain.TipoEvento;
import eventify.api_spring.repository.TipoEventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-eventos")
public class TipoEventoController {

    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    @PostMapping
    public ResponseEntity<TipoEvento> criarTipoEvento(@RequestBody @Valid TipoEvento t) {
        TipoEvento tipoEvento = this.tipoEventoRepository.save(t);
        return ResponseEntity.status(201).body(t);
    }

    @GetMapping
    public ResponseEntity<List<TipoEvento>> exibirTipoEvento() {
         List<TipoEvento> tipoEventos = this.tipoEventoRepository.findAll();
         return ResponseEntity.status(200).body(tipoEventos);
    }
}
