package eventify.api_spring.controller;

import eventify.api_spring.domain.Imagem;
import eventify.api_spring.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping
    public ResponseEntity<Void> salvarImagems(@RequestBody List<MultipartFile> imagens) {
        imagemService.salvarImagems(imagens, 1);
        return ResponseEntity.ok().build();
    }
}
