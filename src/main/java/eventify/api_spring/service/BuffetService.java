package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.dto.*;
import eventify.api_spring.mapper.BuffetMapper;
import eventify.api_spring.repository.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_buffet_info");
        List<Object[]> result = query.getResultList();

        Map<String, List<BuffetInfoDto>> buffetInfoMap = new HashMap<>();

        for (Object[] row : result) {
            Integer id = (Integer) row[0];
            List<String> descricoes = Arrays.asList(((String) row[1]).split(","));
            String nome = (String) row[2];
            BigDecimal precoMediaDiaria = (BigDecimal) row[3];
            Double notaMediaAvaliacao = (Double) row[4];
            List<String> caminhos = Arrays.asList(((String) row[5]).split(","));

            BuffetInfoDto buffetInfo = new BuffetInfoDto(id, descricoes, nome, precoMediaDiaria.doubleValue(), notaMediaAvaliacao, caminhos);

            for (String tipoEvento : descricoes) {
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

        verificarBuffet(buffet);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }

        buffet.setCaminhoComprovante(buffet.getCaminhoComprovante());
        buffet.setDataCriacao(LocalDate.now());
        buffet.setEndereco(endereco);
        buffet.setVisivel(true);
        buffetRepository.save(buffet);
        return buffet;
    }

    public void verificarBuffet(Buffet buffet) {
        if (Objects.isNull(buffet.getNome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome não pode ser nulo");
        }else  if(buffet.getDescricao().length() > 500){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A descrição não pode ter mais de 500 caracteres");
        }
        else if (Objects.isNull(buffet.getDescricao())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Descrição não pode ser nulo");
        } else if (Objects.isNull(buffet.getTamanho())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tamanho não pode ser nulo");
        } else if (Objects.isNull(buffet.getPrecoMedioDiaria())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Preço médio diária não pode ser nulo");
        } else if (Objects.isNull(buffet.getQtdPessoas())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pessoas não pode ser nulo");
        } else if (Objects.isNull(buffet.getCaminhoComprovante())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Comprovante não pode ser nulo");
        } else if (buffet.getFaixaEtarias().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Faixa etária não pode ser nulo");
        } else if (buffet.getTiposEventos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipos de eventos não pode ser nulo");
        } else if (buffet.getServicos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Serviços não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id do usuário não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getNome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getSenha())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Senha não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getDataCriacao())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data de criação não pode ser nulo");
        } else if (Objects.isNull(buffet.getUsuario().getTipoUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo de usuário não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getCep())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cep não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getLogradouro())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Logradouro não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getNumero())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Número não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getBairro())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bairro não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getCidade())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cidade não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getUf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Estado não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getLongitude())) {
            buffet.getEndereco().setLongitude(0.0);
        } else if (Objects.isNull(buffet.getEndereco().getLatitude())) {
            buffet.getEndereco().setLatitude(0.0);
        }
    }

    public Buffet atualizar(Integer idBuffet, Buffet buffet) {
        if (Objects.isNull(idBuffet)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O id do buffet não pode ser nulo");
        }
        if(buffet.getDescricao().length() > 500){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A descrição não pode ter mais de 500 caracteres");
        }
        if (buffetRepository.existsById(idBuffet)) {
            Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

            if (buffetOpt.isPresent()) {
                buffet.setId(idBuffet);
                Buffet buffetDadosBanco = buffetOpt.get();

                Optional<Usuario> usuarioOpt = usuarioRepository.findById(buffetDadosBanco.getUsuario().getId());

                if (usuarioOpt.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
                } else {
                    Usuario usuario = usuarioOpt.get();
                    buffet.setUsuario(usuario);
                }
                Optional<Endereco> enderecoOpt = enderecoRepository.findById(buffetDadosBanco.getEndereco().getId());

                Endereco endereco;

                if (enderecoOpt.isEmpty()) {
                    endereco = buffet.getEndereco();
                    endereco.setDataCriacao(LocalDate.now());
                    enderecoRepository.save(endereco);
                } else {
                    endereco = enderecoOpt.get();
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
        } else if (Objects.isNull(buffet.getEndereco().getCep())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O cep não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getLogradouro())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Logradouro não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getNumero())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Número não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getBairro())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bairro não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getCidade())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cidade não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getUf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Estado não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getLongitude())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Longitude não pode ser nulo");
        } else if (Objects.isNull(buffet.getEndereco().getLatitude())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Longitude não pode ser nulo");
        }

        buffet.setVisivel(buffetBanco.isVisivel());
        buffet.setDataCriacao(buffetBanco.getDataCriacao());
        return buffet;
    }

}
