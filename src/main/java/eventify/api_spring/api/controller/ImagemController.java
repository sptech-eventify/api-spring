package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Imagem;
import eventify.api_spring.service.ImagemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/imagem")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="7. Imagem", description="Controller com os endpoints que controlam o fluxo de imagens do sistema")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping
    public ResponseEntity<Void> salvarImagems(@RequestParam List<MultipartFile> imagens, @RequestParam Integer idBuffet) {
        if (imagemService.salvarImagems(imagens, idBuffet)){
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }
}
