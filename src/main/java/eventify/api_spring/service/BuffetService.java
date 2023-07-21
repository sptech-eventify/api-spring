package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.dto.*;
import eventify.api_spring.mapper.BuffetMapper;
import eventify.api_spring.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class BuffetService {
    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ImagemRepository imagemRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EntityManager entityManager;

    public List<BuffetDtoResposta> listar() {
        List<Buffet> buffets = buffetRepository.findAll();
        List<BuffetDtoResposta> buffetsDto = new ArrayList<>();

        for (Buffet buffet : buffets) {
            List<AgendaDto> agendasDto = buffet.getAgendas().stream().map(agenda -> new AgendaDto(agenda.getId(), agenda.getData())).toList();

            buffetsDto.add(new BuffetDtoResposta(
                    buffet.getId(),
                    buffet.getNome(),
                    buffet.getDescricao(),
                    buffet.getPrecoMedioDiaria(),
                    buffet.getEndereco(),
                    buffet.getTamanho(),
                    buffet.getQtdPessoas(),
                    buffet.getCaminhoComprovante(),
                    buffet.isResidenciaComprovada(),
                    buffet.getFaixaEtarias(),
                    buffet.getTiposEventos(),
                    buffet.getServicos(),
                    buffet.getUsuario().getNome(),
                    agendasDto,
                    buffet.getImagemDto()
            ));
        }

        return buffetsDto;
    }

    public BuffetDtoResposta buscarBuffet(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        return buffetOpt.map(BuffetMapper::toDto).orElse(null);
    }

    public List<Buffet> getBuffetPorPesquisaNome(String q) {
        return buffetRepository.findByNomeContainingIgnoreCase(q);
    }

    public List<String> getTipoEventos() {
        List<Buffet> buffets = buffetRepository.findAll();
        List<String> tipos = new ArrayList<>();
        for (Buffet buffet : buffets) {
            for (TipoEvento evento : buffet.getTiposEventos()) {
                if (!tipos.contains(evento.getDescricao())) {
                    tipos.add(evento.getDescricao());
                }
            }
        }

        return tipos;
    }

    public Double getAvaliacaoEvento(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            return eventoRepository.findAvaliacaoByBuffet(buffet);
        }
        return null;
    }

    public List<Imagem> pegarCaminhoImagem(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            return imagemRepository.findByBuffet(buffet);
        }
        return null;
    }

    public List<DataDto> pegarDatasOcupadas(int idBuffet) {
        List<DataDto> datasDto = new ArrayList<>();
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isPresent()) {
            Buffet buffet = buffetOpt.get();
            List<LocalDate> datas = eventoRepository.findAllDataByBuffet(buffet);
            for (LocalDate data : datas) {
                datasDto.add(new DataDto(data.getYear(), data.getMonthValue(), data.getDayOfMonth()));
            }
            return datasDto;
        }
        return null;
    }

    public List<Long> pegarTaxaDeAbandono(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        List<Long> valores = new ArrayList<>();
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_abandono_reserva(6, %d);", idBuffet));
        Object[] result = (Object[]) query.getSingleResult();
        valores.add((Long) result[0]);
        valores.add((Long) result[1]);
        return valores;
    }

    public List<Object> pegarTaxaDeSatisfacao(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        List<Object> valores = new ArrayList<>();
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_satisfacao(6, %d);", idBuffet));
        Object[] result = (Object[]) query.getSingleResult();
        valores.add(result[0]);
        valores.add(result[1]);
        return valores;
    }

    public List<Object> pegarMovimentacaoFinanceira(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        List<Object> valores = new ArrayList<>();
        Query query = entityManager.createNativeQuery(String.format("CALL sp_kpi_movimentacao_financeira(6, %d);", idBuffet));
        Object[] result = (Object[]) query.getSingleResult();
        valores.add(result[0]);
        valores.add(result[1]);
        return valores;
    }

    public List<Object[]> pegarDadosFinanceiro(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        Query query = entityManager.createNativeQuery(String.format("CALL sp_dados_do_buffet(6, %d);", idBuffet));
        return query.getResultList();
    }

    public List<Object[]> pegarAvaliacoes(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        Query query = entityManager.createNativeQuery(String.format("CALL sp_avaliacoes_buffet(6, %d);", idBuffet));
        return query.getResultList();
    }

    public List<Object[]> pegarOrcamentos(int idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
        if (buffetOpt.isEmpty()) {
            return null;
        }
        Query query = entityManager.createNativeQuery(String.format("select nome, evento.data_criacao from evento join usuario on evento.id_contratante = usuario.id where id_buffet = %d and status = 1;", idBuffet));
        return query.getResultList();
    }

    public Map<String, List<BuffetInfoDto>> pegarBuffetInfoPorTipoEvento() {
        List<BuffetInfoDto> buffetInfoLista = buffetRepository.findAllBuffetInfo();

        Map<String, List<BuffetInfoDto>> buffetInfoMap = new HashMap<>();

        for (BuffetInfoDto buffetInfo : buffetInfoLista) {
            for (String tipoEvento : String.valueOf(buffetInfo.tiposEventos()).split(",")) {
                tipoEvento = tipoEvento.toLowerCase();

                List<BuffetInfoDto> buffetInfoList = buffetInfoMap.getOrDefault(tipoEvento, new ArrayList<>());
                buffetInfoList.add(buffetInfo);
                buffetInfoMap.put(tipoEvento, buffetInfoList);
            }
        }

        return buffetInfoMap;
    }

    public BuffetPublicDto buscarBuffetPublico(int idBuffet) {
        return buffetRepository.findBuffetPublicDtoById(idBuffet);
    }


    public Buffet cadastrar(Buffet buffet) {
        Endereco endereco = buffet.getEndereco();
        endereco.setDataCriacao(LocalDate.now());
        enderecoRepository.save(endereco);
        Optional<Usuario> usuario = usuarioRepository.findById(buffet.getUsuario().getId());
        if (usuario.isPresent()) {
            buffet.setUsuario(usuario.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }
        buffet.setDataCriacao(LocalDate.now());
        buffet.setEndereco(endereco);
        buffet.setVisivel(true);
        buffetRepository.save(buffet);
        return buffet;
    }

    public void verificarBuffet(Buffet buffet) {
        if (Objects.isNull(buffet.getNome())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O nome não pode ser nulo");
        } else if (Objects.isNull(buffet.getDescricao())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A descrição não pode ser nula");
        } else if (Objects.isNull(buffet.getTamanho())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O tamanho não pode ser nulo");
        } else if (Objects.isNull(buffet.getPrecoMedioDiaria())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O preço médio da diária não pode ser nulo");
        } else if (Objects.isNull(buffet.getQtdPessoas())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A quantidade de pessoas não pode ser nula");
        } else if (Objects.isNull(buffet.getCaminhoComprovante())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O caminho do comprovante não pode ser nulo");
        }
    }

    public Buffet atualizar(Integer idBuffet, Buffet buffet) {
        if (Objects.isNull(idBuffet)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O id do buffet não pode ser nulo");
        }
        if (buffetRepository.existsById(idBuffet)) {
            Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

            if (buffetOpt.isPresent()) {
                buffet.setId(idBuffet);
                Buffet buffetDadosBanco = buffetOpt.get();
                Optional<Endereco> enderecoOpt = enderecoRepository.findById(buffetDadosBanco.getEndereco().getId());

                Endereco endereco;

                if (enderecoOpt.isEmpty()) {
                    endereco = buffet.getEndereco();
                    endereco.setDataCriacao(LocalDate.now());
                    enderecoRepository.save(endereco);
                } else {
                    endereco = enderecoOpt.get();
                }

                Optional<Usuario> usuario = usuarioRepository.findById(buffet.getUsuario().getId());

                if (usuario.isPresent()) {
                    buffet.setUsuario(usuario.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
                }

                buffet.setEndereco(endereco);
                Buffet buffetAtualizado = atualizaBuffet(buffet, buffetDadosBanco);
                buffetRepository.save(buffetAtualizado);
                return buffetAtualizado;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buffet não encontrado");
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buffet não encontrado");
    }

    public Buffet atualizaBuffet(Buffet buffet, Buffet buffetBanco) {
        if (Objects.isNull(buffet.getNome())) {
            buffet.setNome(buffetBanco.getNome());
        } else if (Objects.isNull(buffet.getDescricao())) {
            buffet.setDescricao(buffetBanco.getDescricao());
        } else if (Objects.isNull(buffet.getTamanho())) {
            buffet.setTamanho(buffetBanco.getTamanho());
        } else if (Objects.isNull(buffet.getPrecoMedioDiaria())) {
            buffet.setPrecoMedioDiaria(buffetBanco.getPrecoMedioDiaria());
        } else if (Objects.isNull(buffet.getQtdPessoas())) {
            buffet.setQtdPessoas(buffetBanco.getQtdPessoas());
        } else if (Objects.isNull(buffet.getCaminhoComprovante())) {
            buffet.setCaminhoComprovante(buffetBanco.getCaminhoComprovante());
        } else if (Objects.isNull(buffet.getResidenciaComprovada())) {
            buffet.setResidenciaComprovada(buffetBanco.getResidenciaComprovada());
        } else if (buffet.getFaixaEtarias().isEmpty()) {
            buffet.setFaixaEtarias(buffetBanco.getFaixaEtarias());
        } else if (buffet.getTiposEventos().isEmpty()) {
            buffet.setTiposEventos(buffetBanco.getTiposEventos());
        } else if (buffet.getServicos().isEmpty()) {
            buffet.setServicos(buffetBanco.getServicos());
        } else if (Objects.isNull(buffet.getUsuario())) {
            buffet.setUsuario(buffetBanco.getUsuario());
        }
        buffet.setVisivel(buffetBanco.isVisivel());
        buffet.setDataCriacao(buffetBanco.getDataCriacao());
        return buffet;
    }

    public List<BuffetInfoDto> pegarBuffetsProprietario(int idUser) {
        return buffetRepository.findAllBuffetProprietario(idUser);
    }
}
