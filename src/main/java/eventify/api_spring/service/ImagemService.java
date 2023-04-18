package eventify.api_spring.service;

import eventify.api_spring.domain.Imagem;
import eventify.api_spring.domain.ImagemChat;
import eventify.api_spring.domain.Mensagem;
import eventify.api_spring.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    public void salvarImagems(List<MultipartFile> imagens, String data, String nano, int idUsuario) {
        List<Imagem> imagems = new ArrayList<>();
        for (MultipartFile m : imagens) {
            try {
                String fileName = data + nano + "id#" +  idUsuario + ".png";
                String userHome = System.getProperty("user.home");
                Path path = Paths.get("userHome\\OneDrive\\Documentos\\Projetos\\web-app\\src\\assets\\imgChats", fileName);

//                Path path = Paths.get("C:\\Users\\luana\\OneDrive\\Documentos\\Projetos\\web-app\\src\\assets\\imgChats", fileName);
                Files.write(path, m.getBytes());
                Imagem imagem = new Imagem();
                imagem.setCaminho(path.toString());
                imagem.setAtivo(true);
                imagem.setNome(fileName);
                imagem.setTipo("png");
                imagem.setDataUpload(LocalDateTime.now());
                imagems.add(imagem);
                imagemRepository.save(imagem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
