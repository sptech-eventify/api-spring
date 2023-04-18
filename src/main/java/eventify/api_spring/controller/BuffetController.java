package eventify.api_spring.controller;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.dto.DataDto;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.service.BuffetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/{idBuffet}/imagem")
    public ResponseEntity<String> pegarCaminhoImagemEvento(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.pegarCaminhoImagem(idBuffet));
    }

    @GetMapping("/datas/{idBuffet}")
    public ResponseEntity<List<DataDto>> pegarDatasOcupadas(@PathVariable int idBuffet) {
        return ResponseEntity.status(200).body(buffetService.pegarDatasOcupadas(idBuffet));
    }

    @PostMapping
    public ResponseEntity<Buffet> cadastrar(@RequestBody @Valid Buffet buffet) {
        buffetService.cadastrar(buffet);
        return ResponseEntity.status(201).body(buffet);
    }

    @PutMapping("/{idBuffet}")
    public ResponseEntity<Buffet> atualizar(@PathVariable int idBuffet, @RequestBody @Valid Buffet buffet) {
        buffet.setId(idBuffet);
        buffetService.atualizar(buffet);
        return ResponseEntity.status(200).body(buffet);
    }
}
