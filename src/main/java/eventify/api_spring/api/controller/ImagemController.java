package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Imagem;
import eventify.api_spring.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagem")
@CrossOrigin(origins = "http://localhost:3000")
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
