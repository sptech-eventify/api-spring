package eventify.api_spring.controller;

import eventify.api_spring.domain.FaixaEtaria;
import eventify.api_spring.repository.FaixaEtariaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faixa-etarias")
public class FaixaEtariaController {

    @Autowired
    private FaixaEtariaRepository faixaEtariaRepository;

    @PostMapping
    public ResponseEntity<FaixaEtaria> criarFaixaEtaria(@RequestBody @Valid FaixaEtaria f) {
        FaixaEtaria faixaEtaria = this.faixaEtariaRepository.save(f);
        return ResponseEntity.status(201).body(f);
    }

    @GetMapping
    public ResponseEntity<List<FaixaEtaria>> exibirFaixaEtaria() {
         List<FaixaEtaria> faixaEtaria = this.faixaEtariaRepository.findAll();
         return ResponseEntity.status(200).body(faixaEtaria);
    }
}
