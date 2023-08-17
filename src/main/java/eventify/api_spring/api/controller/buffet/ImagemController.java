package eventify.api_spring.api.controller.buffet;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;

import eventify.api_spring.service.buffet.ImagemService;

import java.util.List;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/imagem")
@CrossOrigin(origins = {"http://localhost:5173", "http://26.69.189.151:5173"})
@Tag(name="7. Imagem", description="Controller com os endpoints que controlam o fluxo de imagens do sistema")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping
    public ResponseEntity<Void> salvarImagems(@RequestParam List<MultipartFile> imagens, @RequestParam Integer idBuffet) {
        imagemService.salvarImagens(imagens, idBuffet);

        return ok().build();
    }
}
