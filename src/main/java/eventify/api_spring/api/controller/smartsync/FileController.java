package eventify.api_spring.api.controller.smartsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import eventify.api_spring.service.smartsync.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RequestMapping("/file")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@SecurityRequirement(name = "requiredAuth")
@RestController
@Tag(name="Dados", description="Controller com os para exportação de dados")
public class FileController {
    @Autowired
    private FileService fileService;

    @Transactional
    @GetMapping("/{idBuffet}")
    public ResponseEntity<?> retornarDados(@PathVariable Integer idBuffet) {
        try {
            File dados = fileService.retornarCsv(idBuffet);
            System.out.println(dados.getAbsolutePath());
            
            byte[] bytes = Files.readAllBytes(dados.toPath());
            ByteArrayResource resource = new ByteArrayResource(bytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dados.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytes.length)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
