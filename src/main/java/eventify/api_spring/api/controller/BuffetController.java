package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.dto.DataDto;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.service.BuffetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="2. Buffet", description="Controller com os endpoints de buffet")
public class BuffetController {

    @Autowired
    private BuffetService buffetService;

    @GetMapping
    public ResponseEntity<List<Buffet>> listar() {
        List<Buffet> buffets = buffetService.listar();
        if (buffets.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(buffets);
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

    @SecurityRequirement(name = "requiredAuth")
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
