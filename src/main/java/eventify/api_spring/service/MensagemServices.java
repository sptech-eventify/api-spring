package eventify.api_spring.service;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.ImagemChat;
import eventify.api_spring.domain.Mensagem;
import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.MensagemDto;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.ImagemChatRepository;
import eventify.api_spring.repository.MensagemRepository;
import eventify.api_spring.repository.UsuarioRepository;
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
import java.util.Optional;

@Service
public class MensagemServices {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MensagemRepository mensagemRepository;
    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private ImagemChatRepository imagemChatRepository;

    public Mensagem mandarMensagem(int idUsuario, int idBuffet, String text, boolean whoSended, List<MultipartFile> imagens) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (usuarioOpt.isPresent() && buffetOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Buffet buffet = buffetOpt.get();
            Mensagem mensagem = new Mensagem();
            mensagem.setMensagem(text);
            mensagem.setMandadoPor(whoSended);
            mensagem.setData(LocalDateTime.now());
            mensagem.setUsuario(usuario);
            mensagem.setBuffet(buffet);
            if (imagens != null) {
                return salvarImagems(imagens, LocalDate.now().toString(), String.valueOf(LocalDateTime.now().getNano()), idUsuario, mensagemRepository.save(mensagem));
            } else {
                return mensagemRepository.save(mensagem);
            }
        }
        return null;
    }

    public Mensagem salvarImagems(List<MultipartFile> imagens, String data, String nano, int idUsuario, Mensagem mensagem) {
        List<ImagemChat> imagemChats = new ArrayList<>();
        for (MultipartFile imagem : imagens) {
            try {
                String nomeSistemaOperacional = System.getProperty("os.name");

                String fileName = data + nano + "id#" +  idUsuario + ".png";
                File currentDirectory = new File(System.getProperty("user.dir"));
                File diretorioPai = currentDirectory.getParentFile();
                System.setProperty("user.dir", diretorioPai.getAbsolutePath());

                String caminho = "";

                if (Objects.equals(nomeSistemaOperacional, "Linux")) {
                    caminho = diretorioPai + "//web-app//public//img";
                }
                else if (Objects.equals(nomeSistemaOperacional, "Windows")) {
                    caminho = diretorioPai + "\\web-app\\public\\img";
                }

                Path path = Paths.get(caminho, fileName);
                Files.write(path, imagem.getBytes());
                ImagemChat imagemChat = new ImagemChat();
                imagemChat.setCaminho(path.toString());
                imagemChat.setAtivo(true);
                imagemChat.setNome(fileName);
                imagemChat.setTipo("png");
                imagemChat.setDataUpload(LocalDateTime.now());
                imagemChat.setMensagem(mensagem);
                imagemChats.add(imagemChat);
                mensagem.getImagens().add(imagemChat);
                imagemChatRepository.save(imagemChat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mensagem;
    }

    public List<MensagemDto> listarMensagemPorUsuario(int idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isPresent()) {
            Usuario remetente = usuarioOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByUsuario(remetente);
            if (mensagems.isEmpty()) {
                return null;
            }
            return mensagems.stream()
                    .map(m -> new MensagemDto(m.getId() ,m.getMensagem(), m.isMandadoPor(), m.getData(), m.getUsuario().getId(), m.getBuffet().getId(), m.getImagensDto()))
                    .toList();
        }
        return null;
    }

    public List<MensagemDto> listarMensagemPorBuffet(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByBuffet(buffet);
            if (mensagems.isEmpty()) {
                return null;
            }
            return mensagems.stream()
                    .map(m -> new MensagemDto(m.getId(), m.getMensagem(), m.isMandadoPor(), m.getData(), m.getUsuario().getId(), m.getBuffet().getId(), m.getImagensDto()))
                    .toList();
        }
        return null;
    }

    public List<MensagemDto> listarMensagemPorUsuarioBuffet(int idUsuario, int idBuffet) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (usuarioOpt.isPresent() && buffetOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Buffet buffet = buffetOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByUsuarioBuffet(usuario, buffet);
            if (mensagems.isEmpty()) {
                return null;
            }
            return mensagems.stream()
                    .map(m -> new MensagemDto(m.getId() ,m.getMensagem(), m.isMandadoPor(), m.getData(), m.getUsuario().getId(), m.getBuffet().getId(), m.getImagensDto()))
                    .toList();
        }
        return null;
    }

}
