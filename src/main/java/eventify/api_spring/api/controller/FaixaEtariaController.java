package eventify.api_spring.api.controller;

import eventify.api_spring.domain.FaixaEtaria;
import eventify.api_spring.repository.FaixaEtariaRepository;
import eventify.api_spring.service.FaixaEtariaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faixa-etarias")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="8. Faixa Etária", description="Controller com os endpoints que controlam as configuraçõs de faixa etária do sistema")
public class FaixaEtariaController {
    @Autowired
    private FaixaEtariaService faixaEtariaService;

    @SecurityRequirement(name = "requiredAuth")
    @PostMapping
    public ResponseEntity<FaixaEtaria> criarFaixaEtaria(@RequestBody @Valid FaixaEtaria f) {
        faixaEtariaService.criarFaixaEtaria(f);
        return ResponseEntity.status(201).body(f);
    }

    @GetMapping
    public ResponseEntity<List<FaixaEtaria>> exibirFaixaEtaria() {
         List<FaixaEtaria> faixaEtaria = this.faixaEtariaService.exibirFaixaEtaria();
         if (faixaEtaria.isEmpty()) {
             return ResponseEntity.status(204).build();
         }
         return ResponseEntity.status(200).body(faixaEtaria);
    }
}
