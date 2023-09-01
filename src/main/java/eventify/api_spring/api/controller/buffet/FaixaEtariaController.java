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
@RequestMapping("/faixas-etarias")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="Faixa Etária", description="Controller de endpoints das configuraçõs de faixa etária do sistema")
public class FaixaEtariaController {
    @Autowired
    private FaixaEtariaService faixaEtariaService;

    @GetMapping
    public ResponseEntity<List<FaixaEtaria>> faixasEtarias() {
        List<FaixaEtaria> faixaEtaria = faixaEtariaService.faixasEtarias();

        return ok(faixaEtaria);
    }
}
