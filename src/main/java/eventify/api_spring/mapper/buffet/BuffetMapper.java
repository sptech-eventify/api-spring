package eventify.api_spring.mapper.buffet;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;
import eventify.api_spring.mapper.usuario.UsuarioMapper;
import eventify.api_spring.mapper.agenda.AgendaMapper;
import eventify.api_spring.mapper.endereco.EnderecoMapper;

@Component
public class BuffetMapper {
    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private AgendaMapper agendaMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired 
    private ImagemMapper imagemMapper;

    public BuffetRespostaDto toRespostaDto(Buffet domain){
        BuffetRespostaDto dto = new BuffetRespostaDto();
        
        dto.setId(domain.getUsuario().getId());
        dto.setNome(domain.getNome());
        dto.setDescricao(domain.getDescricao());
        dto.setPrecoMedioDiaria(domain.getPrecoMedioDiaria());
        dto.setEndereco(enderecoMapper.toDto(domain.getEndereco()));
        dto.setTamanho(domain.getTamanho());
        dto.setQtdPessoas(domain.getQtdPessoas());
        dto.setCaminhoComprovante(domain.getCaminhoComprovante());
        dto.setResidenciaComprovada(domain.isResidenciaComprovada());
        dto.setFaixasEtarias(domain.getFaixaEtarias());
        dto.setTiposEventos(domain.getTiposEventos());
        dto.setServicos(domain.getServicos());
        dto.setUsuario(UsuarioMapper.toDevolverDto(domain.getUsuario()));
        dto.setAgendas(domain.getAgendas().stream().map(agendaMapper::toDto).collect(Collectors.toList()));
        dto.setImagens(domain.getImagens().stream().map(imagemMapper::toDto).collect(Collectors.toList()));

        return dto;
    }

    public BuffetPublicoDto toPublicoDto(Buffet domain){
        BuffetPublicoDto dto = new BuffetPublicoDto();
        
        dto.setNome(domain.getNome());
        dto.setNomeProprietario(domain.getUsuario().getNome());
        dto.setDescricao(domain.getDescricao());
        dto.setImagens(domain.getImagens().stream().map(imagemMapper::toDto).collect(Collectors.toList()));
        dto.setPrecoMedioDiaria(domain.getPrecoMedioDiaria());
        dto.setNotaMediaAvaliacao(null);
        dto.setServicos(domain.getServicos().stream().collect(Collectors.toList()));
        dto.setFaixasEtarias(domain.getFaixaEtarias().stream().collect(Collectors.toList()));
        dto.setTiposEventos(domain.getTiposEventos().stream().collect(Collectors.toList()));
        dto.setLatitude(domain.getEndereco().getLatitude());
        dto.setLongitude(domain.getEndereco().getLongitude());
        dto.setLogradouro(domain.getEndereco().getLogradouro());
        dto.setNumero(domain.getEndereco().getNumero());
        dto.setBairro(domain.getEndereco().getBairro());
        dto.setCidade(domain.getEndereco().getCidade());
        dto.setUf(domain.getEndereco().getUf());
        dto.setCep(domain.getEndereco().getCep());

        return dto;
    }

    public BuffetResumoDto toResumoDto(Buffet domain){
        BuffetResumoDto dto = new BuffetResumoDto();
        
        dto.setId(domain.getId());
        dto.setTiposEventos(domain.getTiposEventos().stream().collect(Collectors.toList()));
        dto.setNome(domain.getNome());
        dto.setPrecoMedioDiaria(domain.getPrecoMedioDiaria());
        dto.setTamanho(domain.getTamanho());
        dto.setQtdPessoas(domain.getQtdPessoas());
        dto.setNotaMediaAvaliacao(null);
        dto.setImagens(domain.getImagens().stream().map(imagemMapper::toDto).collect(Collectors.toList()));

        return dto;
    }

    public static Usuario of(UsuarioCadastrarDto usuarioCadastrarDTO) {
        Usuario usuario = new Usuario();
        usuario.setIsAtivo(false);
        usuario.setIsBanido(false);
        usuario.setEmail(usuarioCadastrarDTO.getEmail());
        usuario.setTipoUsuario(usuarioCadastrarDTO.getTipoUsuario());
        usuario.setNome(usuarioCadastrarDTO.getNome());
        usuario.setCpf(usuarioCadastrarDTO.getCpf());
        usuario.setTipoUsuario(usuarioCadastrarDTO.getTipoUsuario());
        usuario.setSenha(usuarioCadastrarDTO.getSenha());
        usuario.setDataCriacao(LocalDateTime.now());
        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto(usuario.getId(),
                usuario.getTipoUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                token,
                usuario.getFoto());
        return usuarioTokenDto;
    }
}