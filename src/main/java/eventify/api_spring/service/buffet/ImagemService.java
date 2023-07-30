package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Imagem;
import eventify.api_spring.exception.http.UnsupportedMediaException;
import eventify.api_spring.repository.ImagemRepository;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public boolean salvarImagens(List<MultipartFile> imagens, Integer idBuffet) {
        List<Imagem> imagensSalvas = new ArrayList<>();

        for (MultipartFile imagem : imagens) {
            try {
                Imagem imagemSalva = fileStorageService.salvarImagem(imagem, idBuffet);
                imagensSalvas.add(imagemSalva);
            } catch (IOException e) {
                e.printStackTrace();
                
                throw new UnsupportedMediaException("Não foi possível salvar a imagem");
            }
        }

        imagemRepository.saveAll(imagensSalvas);
        return true;
    }
}