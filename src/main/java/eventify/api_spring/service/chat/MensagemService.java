package eventify.api_spring.service.chat;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.chat.ImagemChat;
import eventify.api_spring.domain.chat.Mensagem;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.chat.ChatListaDto;
import eventify.api_spring.dto.chat.MensagemDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.exception.http.UnsupportedMediaException;
import eventify.api_spring.mapper.chat.MensagemMapper;
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
public class MensagemService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MensagemRepository mensagemRepository;
    
    @Autowired
    private BuffetRepository buffetRepository;
    
    @Autowired
    private ImagemChatRepository imagemChatRepository;

    public MensagemDto mandarMensagem(int idUsuario, int idBuffet, String text, boolean whoSended, List<MultipartFile> imagens) {
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

            if (Objects.nonNull(imagens)) {
                Mensagem mensagemEnviada = salvarImagems(imagens, LocalDate.now().toString(), String.valueOf(LocalDateTime.now().getNano()), idUsuario, mensagemRepository.save(mensagem));
                MensagemDto mensagemDto = MensagemMapper.toDto(mensagemEnviada);
               
                return mensagemDto;
            } else {
                Mensagem mensagemEnviada = mensagemRepository.save(mensagem);
                MensagemDto mensagemDto = MensagemMapper.toDto(mensagemEnviada);

                return mensagemDto;
            }
        }

        throw new NotFoundException("Usuário ou Buffet não encontrado");
    }

    public Mensagem salvarImagems(List<MultipartFile> imagens, String data, String nano, Integer idUsuario, Mensagem mensagem) {
        List<ImagemChat> imagemChats = new ArrayList<>();

        for (MultipartFile imagem: imagens) {
            try {
                String nomeSistemaOperacional = System.getProperty("os.name");
                String fileName = data + nano + "id" +  idUsuario + ".png";
                File currentDirectory = new File(System.getProperty("user.dir"));
                File diretorioPai = currentDirectory.getParentFile();
                String caminho = "";
                System.setProperty("user.dir", diretorioPai.getAbsolutePath());

                if (Objects.equals(nomeSistemaOperacional, "Linux")) {
                    caminho = diretorioPai + "//web-app//public//img";
                } else if (Objects.equals(nomeSistemaOperacional, "Windows")) {
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
                throw new UnsupportedMediaException("Não foi possível salvar a imagem");
            }
        }
        
        return mensagem;
    }

    public List<MensagemDto> listarMensagemPorUsuario(Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (usuarioOpt.isPresent()) {
            Usuario remetente = usuarioOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByUsuario(remetente);

            if (mensagems.isEmpty()) {
                throw new NoContentException("Não há mensagens para este usuário");
            }

            return mensagems.stream()
                    .map(MensagemMapper::toDto)
                    .toList();
        }

        throw new NotFoundException("Usuário não encontrado");
    }

    public List<MensagemDto> listarMensagemPorBuffet(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            List<Mensagem> mensagems = mensagemRepository.findAllByBuffet(buffet);

            if (mensagems.isEmpty()) {
                throw new NoContentException("Não há mensagens para este buffet");
            }

            return mensagems.stream()
                    .map(MensagemMapper::toDto)
                    .toList();
        }

        throw new NotFoundException("Buffet não encontrado");
    }

    public List<MensagemDto> listarMensagemPorUsuarioBuffet(Integer idUsuario, Integer idBuffet) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (usuarioOpt.isPresent() && buffetOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Buffet buffet = buffetOpt.get();

            List<Mensagem> mensagems = mensagemRepository.findAllByUsuarioBuffet(usuario, buffet);

            if (mensagems.isEmpty()) {
                throw new NoContentException("Não há mensagens para este usuário e buffet");
            }

            return mensagems.stream()
                    .map(MensagemMapper::toDto)
                    .toList();
        }

        throw new NotFoundException("Usuário ou Buffet não encontrado");
    }

    public List<ChatListaDto> listarChatsDoUsuario(Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if(usuarioOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        
        List<ChatListaDto> chats = mensagemRepository.findAllChatByUsuario(idUsuario);

        if (chats.isEmpty()) {
            throw new NoContentException("Não há chats para este usuário");
        }

        return chats;
    }

    public List<ChatListaDto> listarChat() {
        List<ChatListaDto> chats = mensagemRepository.findAllChatListaDto();

        if (chats.isEmpty()) {
            throw new NoContentException("Não há chats");
        }

        return chats;
    }

    public Integer checarQtdMensagens(Integer idUsuario, Integer idBuffet) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (usuarioOpt.isEmpty() || buffetOpt.isEmpty()) {
            throw new NotFoundException("Usuário ou Buffet não encontrado");
        }

        Integer quantidade = mensagemRepository.countMensagemByBuffetAndUsuario(buffetOpt.get(), usuarioOpt.get());

        if (Objects.isNull(quantidade)) {
            throw new NoContentException("Não há mensagens para este usuário e buffet");
        }

        return quantidade;
    }

}
