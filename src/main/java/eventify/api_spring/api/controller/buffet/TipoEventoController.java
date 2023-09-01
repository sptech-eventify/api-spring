package eventify.api_spring.api.controller.buffet;

import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.service.buffet.TipoEventoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;
import java.util.List;

@RestController
@RequestMapping("/tipos-eventos")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="Tipo", description="Controller com os endpoints que controlam os tipos de evento do sistema")
public class TipoEventoController {
    @Autowired
    private TipoEventoService tipoEventoService;

    @GetMapping
    public ResponseEntity<List<TipoEvento>> tiposEventos() {
        List<TipoEvento> tiposEventos = tipoEventoService.tiposEventos();

        return ok(tiposEventos);
    }
}
