package eventify.api_spring.service.buffet;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import eventify.api_spring.domain.buffet.Imagem;

@Service
public class FileStorageService {
    public Imagem salvarImagem(MultipartFile arquivo, Integer idBuffet) throws IOException {
        String nomeArquivo = generateUniqueFileName(arquivo, idBuffet);
        String caminho = buildFilePath(nomeArquivo);

        Path path = Paths.get(caminho);
        Files.write(path, arquivo.getBytes());

        Imagem imagem = new Imagem();
        imagem.setCaminho(path.toString());
        imagem.setAtivo(true);
        imagem.setNome(nomeArquivo);
        imagem.setTipo("png");
        imagem.setDataUpload(LocalDateTime.now());

        return imagem;
    }

    private String generateUniqueFileName(MultipartFile arquivo, int idBuffet) {
        String data = LocalDate.now().toString();
        String randomUUID = UUID.randomUUID().toString().replaceAll("-", "");
        return data + randomUUID + "id" + idBuffet + ".png";
    }

    private String buildFilePath(String nomeArquivo) {
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();
        String diretorioPai = new File(System.getProperty("user.dir")).getParent();
        String separator = File.separator;

        if (sistemaOperacional.contains("linux")) {
            return diretorioPai + separator + "web-app" + separator + "public" + separator + "img-buffet" + separator + nomeArquivo;
        } else if (sistemaOperacional.contains("windows")) {
            return diretorioPai + separator + "web-app" + separator + "public" + separator + "img-buffet" + separator + nomeArquivo;
        }

        throw new UnsupportedOperationException("Sistema operacional não suportado: " + sistemaOperacional);
    }
}
