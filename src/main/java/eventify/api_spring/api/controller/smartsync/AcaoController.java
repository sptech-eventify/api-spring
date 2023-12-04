package eventify.api_spring.api.controller.smartsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

import java.util.List;

import eventify.api_spring.dto.smartsync.AcaoDto;
import eventify.api_spring.service.smartsync.AcaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/acoes")
@SecurityRequirement(name = "requiredAuth")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = {"Access-Control-Expose-Headers", "Access-Token", "Uid"})
public class AcaoController {
    @Autowired
    private AcaoService acaoService;

    @GetMapping
    public ResponseEntity<List<AcaoDto>> retornarAcoes() {
        List<AcaoDto> acoes = acaoService.retornarAcoes();

        return ok(acoes);
    }
}
