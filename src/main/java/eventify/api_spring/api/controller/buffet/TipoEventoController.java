package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.service.buffet.TipoEventoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-eventos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Tipo", description="Controller com os endpoints que controlam os tipos de evento do sistema")
public class TipoEventoController {
    @Autowired
    private TipoEventoService tipoEventoService;

    @GetMapping
    public ResponseEntity<List<TipoEvento>> exibirTipoEvento() {
        List<TipoEvento> tipoEventos = this.tipoEventoService.exibirTipoEvento();

        if(tipoEventos.isEmpty()){
            return notFound().build();
        }

        return ok(tipoEventos);
    }
}
