package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    public void salvarImagems(List<MultipartFile> imagens,int idBuffet) {

        List<Imagem> imagems = new ArrayList<>();
        String data = LocalDate.now().toString();
        String nano = String.valueOf(LocalDateTime.now().getNano());

        for (MultipartFile m : imagens) {
            try {
                String fileName = data + nano + "id#" +  idBuffet + ".png";
                String userHome = System.getProperty("user.home");
                Path path = Paths.get(userHome +"\\OneDrive\\Documentos\\Projetos\\web-app\\src\\assets\\imgChats", fileName);
                Files.write(path, m.getBytes());
                Imagem imagem = new Imagem();
                imagem.setCaminho(path.toString());
                imagem.setAtivo(true);
                imagem.setNome(fileName);
                imagem.setTipo("png");
                imagem.setDataUpload(LocalDateTime.now());
                imagems.add(imagem);
                imagemRepository.saveAll(imagems);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
