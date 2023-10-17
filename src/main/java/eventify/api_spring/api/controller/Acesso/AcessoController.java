package eventify.api_spring.api.controller.Acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eventify.api_spring.service.Acesso.AcessoService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/logAcesso")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping("/{idPagina}")
    public ResponseEntity<Void> logAcesso(@PathVariable Integer idPagina) {
        acessoService.gerarLog(idPagina);

        return ok().build();
    }
}