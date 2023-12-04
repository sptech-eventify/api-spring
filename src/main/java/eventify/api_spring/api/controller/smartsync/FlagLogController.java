package eventify.api_spring.api.controller.smartsync;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventify.api_spring.domain.smartsync.FlagLog;
import eventify.api_spring.dto.smartsync.FlagLogCriacaoDto;
import eventify.api_spring.dto.smartsync.FlagLogDto;
import eventify.api_spring.service.smartsync.FlagLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/flag-log")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = {"Access-Control-Expose-Headers", "Access-Token", "Uid"})
@SecurityRequirement(name = "requiredAuth")
public class FlagLogController {
    @Autowired
    private FlagLogService flagLogService;

    @GetMapping("/{idExecutor}")
    public ResponseEntity<List<FlagLogDto>> retornarFlagLog(@PathVariable Integer idExecutor, @RequestParam("isFuncionario") Boolean isFuncionario) {
        List<FlagLogDto> flagLogDto = flagLogService.retornarFlagLog(idExecutor, isFuncionario);

        return ok(flagLogDto);
    }

    @GetMapping("/secao/{idExecutor}")
    public ResponseEntity<List<FlagLogDto>> retornarFlagLogPorSecao(@PathVariable Integer idExecutor, @RequestParam("isFuncionario") Boolean isFuncionario, @RequestParam("idSecao") Integer idSecao) {
        List<FlagLogDto> flagLogDto = flagLogService.retornarFlagLogPorSecao(idExecutor, isFuncionario, idSecao);
        
        return ok(flagLogDto);
    }

    @GetMapping("/individual/{id}")
    public ResponseEntity<FlagLogDto> retornarFlagLogIndividual(@PathVariable Integer id) {
        FlagLogDto flagLogDto = flagLogService.retornarFlagLogIndividual(id);

        return ok(flagLogDto);
    }

    @PostMapping
    public ResponseEntity<FlagLog> criarFlagLog(@RequestBody FlagLogCriacaoDto flagLogCriacaoDto) {
        System.out.println(flagLogCriacaoDto.getIdTarefa());
        FlagLog flagLog = flagLogService.criarFlagLog(flagLogCriacaoDto);
        URI location = URI.create(String.format("/flag-log/individual/%d", flagLog.getId()));

        return created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFlagLog(@PathVariable Integer id) {
        flagLogService.deletarFlagLog(id);

        return noContent().build();
    }
}
