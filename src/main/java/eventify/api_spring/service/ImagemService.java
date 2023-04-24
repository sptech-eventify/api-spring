package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    public Boolean salvarImagems(List<MultipartFile> imagens, int idBuffet) {

        List<Imagem> imagems = new ArrayList<>();
        String data = LocalDate.now().toString();
        String nano = String.valueOf(LocalDateTime.now().getNano());

        for (MultipartFile m : imagens) {
            try {
                String nomeSistemaOperacional = System.getProperty("os.name");

                String fileName = data + nano + "id#" + idBuffet + ".png";
                File currentDirectory = new File(System.getProperty("user.dir"));
                File diretorioPai = currentDirectory.getParentFile();
                System.setProperty("user.dir", diretorioPai.getAbsolutePath());

                String caminho = "";

                if (nomeSistemaOperacional.contains("Linux")) {
                    caminho = diretorioPai + "//web-app//public//img-buffet";
                }
                else if (nomeSistemaOperacional.contains("Windows")) {
                    caminho = diretorioPai + "\\web-app\\public\\img-buffet";
                }

                Path path = Paths.get(caminho, fileName);
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
                return false;
            }
        }
        return true;
    }
}
