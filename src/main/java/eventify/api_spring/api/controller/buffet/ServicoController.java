package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.service.buffet.ServicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

//  Finalizada

import java.util.List;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="Serviço", description="Controller com os endpoints que controlam os serviços do sistema")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> servicos() {
        List<Servico> servicos = this.servicoService.servicos();

        return ok(servicos);
    }
}
