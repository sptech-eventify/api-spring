package eventify.api_spring.api.controller.smartsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eventify.api_spring.service.smartsync.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequestMapping("/file")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@SecurityRequirement(name = "requiredAuth")
@RestController
@Tag(name="Dados", description="Controller com os para exportação de dados")
public class FileController {
    @Autowired
    private FileService fileService;

    @Transactional
    @GetMapping("/transacoes/{idBuffet}")
    public ResponseEntity<byte[]> retornarTransacoes(@PathVariable Integer idBuffet) {
        String dados = fileService.retornarTransacoesCsv(idBuffet);
        String nomeBuffet = "transacoes-" + fileService.retornarNomeBuffet(idBuffet);
        byte[] csvBytes = dados.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(nomeBuffet + ".csv").build());
        
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/eventos/{idBuffet}")
    public ResponseEntity<byte[]> retornarEventos(@PathVariable Integer idBuffet) {
        String dados = fileService.retornarEventosCsv(idBuffet);
        String nomeBuffet = "eventos-" + fileService.retornarNomeBuffet(idBuffet);
        byte[] csvBytes = dados.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(nomeBuffet + ".csv").build());
        
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
    
    
}
