package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.FaixaEtaria;
import eventify.api_spring.service.buffet.FaixaEtariaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;

import java.util.List;

@RestController
@RequestMapping("/faixa-etarias")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="8. Faixa Etária", description="Controller com os endpoints que controlam as configuraçõs de faixa etária do sistema")
public class FaixaEtariaController {
    @Autowired
    private FaixaEtariaService faixaEtariaService;

    @GetMapping
    public ResponseEntity<List<FaixaEtaria>> exibirFaixaEtaria() {
        List<FaixaEtaria> faixaEtaria = this.faixaEtariaService.exibirFaixaEtaria();

        if (faixaEtaria.isEmpty()) {
             return noContent().build();
        }

        return ok(faixaEtaria);
    }
}
