package eventify.sptech.apispring.Services;

import eventify.sptech.apispring.Entities.*;
import eventify.sptech.apispring.Entities.Dto.MensagemDto;
import eventify.sptech.apispring.Repositories.BuffetRepository;
import eventify.sptech.apispring.Repositories.ImagemChatRepository;
import eventify.sptech.apispring.Repositories.MensagemRepository;
import eventify.sptech.apispring.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                String fileName = data + nano + "id#" +  idUsuario + ".png";
                Path path = Paths.get("C:\\Users\\luana\\OneDrive\\Documentos\\Projetos\\web-app\\src\\assets\\imgChats", fileName);
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
