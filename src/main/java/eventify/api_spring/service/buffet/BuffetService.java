package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.exception.http.ConflictException;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.buffet.BuffetMapper;
import eventify.api_spring.mapper.buffet.ImagemMapper;
import eventify.api_spring.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    private ImagemMapper imagemMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private BuffetMapper buffetMapper;

    public Buffet buscarBuffetPorId(Integer buffetId) {
        Optional<Buffet> buffet = buffetRepository.findById(buffetId);
        
        return buffet.orElseThrow(() -> new NotFoundException("Buffet não encontrado na base de dados"));
    }

    public List<BuffetRespostaDto> listarBuffets() {
        List<BuffetRespostaDto> buffets = buffetRepository.findAll().stream().map(buffetMapper::toRespostaDto).toList();
        
        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        return buffets;
    }

    public List<BuffetRespostaDto> listarBuffetsPublicos() {
        List<BuffetRespostaDto> buffets = buffetRepository.findByIsVisivelTrue().stream().map(buffetMapper::toRespostaDto).toList();
        
        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets disponíveis");
        }

        return buffets;
    }

    public BuffetRespostaDto buscarBuffetPorIdResposta(Integer id){
        Buffet buffet = buscarBuffetPorId(id);

        if (Objects.isNull(buffet)) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        return buffetMapper.toRespostaDto(buffet);
    }

    public BuffetRespostaDto buscarBuffetPublicoPorIdResposta(Integer id){
        Buffet buffet = buffetRepository.findByIsVisivelTrueAndId(id);

        if (Objects.isNull(buffet)) {
            throw new NotFoundException("Buffet não encontrado ou não disponível ao público");
        }

        return buffetMapper.toRespostaDto(buffet);
    }

    public Buffet criarBuffet(Buffet buffet) {
        Optional<Usuario> usuario = usuarioRepository.findById(buffet.getUsuario().getId());

        if (usuario.isPresent()) {
            buffet.setUsuario(usuario.get());
        } else {
            throw new NotFoundException("Usuário não encontrado na base de dados");
        }
        
        Endereco endereco = buffet.getEndereco();

        endereco.setDataCriacao(LocalDate.now());
        endereco.setValidado(false);

        enderecoRepository.save(endereco);

        buffet.setDataCriacao(LocalDate.now());
        buffet.setVisivel(false);
        buffetRepository.save(buffet);

        return buffet;
    }

    public Buffet atualizarBuffet(Integer idBuffet, Buffet buffet) {
        Optional<Buffet> buffetOptional = buffetRepository.findById(idBuffet);

        if (buffetOptional.isPresent()) {
            Buffet buffetBanco = buffetOptional.get();
                
            if(Objects.isNull(buffetBanco.getEndereco()) && Objects.nonNull(buffet.getEndereco())){
                Endereco endereco = buffet.getEndereco();

                endereco.setDataCriacao(LocalDate.now());
                endereco.setValidado(false);
                enderecoRepository.save(endereco);

                buffetBanco.setEndereco(endereco);
            }

            buffetRepository.save(buffetBanco);

            return buffetBanco;
        } else {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }
    }

    public Buffet deletarBuffet(Integer id) {
        Optional<Buffet> buffet = buffetRepository.findById(id);

        if (buffet.isPresent()) {
            Buffet buffetBanco = buffet.get();
            
            if (buffetBanco.getAgendas().isEmpty()){
                buffetRepository.delete(buffetBanco);
            } else {
                throw new ConflictException("Buffet possui eventos marcados");
            }

            return buffetBanco;
        } else {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }
    }

    public Double avaliacaoBuffet(Integer idBuffet) {
        Buffet buffet = buffetRepository.findBuffetById(idBuffet);

        if (Objects.isNull(buffet)) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Double avaliacao = eventoRepository.findAvaliacaoByBuffet(buffet);
        
        if (Objects.isNull(avaliacao)) {
            return 0.0;
        }

        return avaliacao;
    }

    public List<ImagemDto> caminhoImagemBuffet(Integer idBuffet) {
        Buffet buffet = buffetRepository.findByIsVisivelTrueAndId(idBuffet);

        if (Objects.nonNull(buffet)) {
            List<ImagemDto> caminhos = imagemRepository.findByBuffet(buffet)
                    .stream().map(imagemMapper::toDto).toList();

            return caminhos;
        }
    
        throw new NotFoundException("Buffet não encontrado ou não disponível ao público");
    }

    public BuffetPublicoDto buscarBuffetPublico(Integer idBuffet) {
        Buffet buffetBanco = buffetRepository.findByIsVisivelTrueAndId(idBuffet);

        if (Objects.isNull(buffetBanco)) {
            throw new NotFoundException("Buffet não encontrado ou não disponível ao público");
        }
        
        BuffetPublicoDto buffet = buffetMapper.toPublicoDto(buffetBanco);
        buffet.setNotaMediaAvaliacao(avaliacaoBuffet(idBuffet));
        
        return buffet;
    }

    public Map<String, List<BuffetResumoDto>> listarBuffetsResumidosPublico() {
        List<Buffet> buffetBanco = buffetRepository.findByIsVisivelTrue();

        if (buffetBanco.isEmpty()) {
            throw new NoContentException("Não há buffets disponíveis");
        }

        List<BuffetResumoDto> buffetsResumo = buffetBanco.stream().map(buffetMapper::toResumoDto).toList();

        buffetsResumo.stream().map(buffetResumo -> {
                        buffetResumo.setNotaMediaAvaliacao(avaliacaoBuffet(buffetResumo.getId()));
                        return buffetResumo;
                    })
                    .collect(Collectors.toList());


        Map<String, List<BuffetResumoDto>> buffetsPorTipoEvento = buffetsResumo.stream()

                .flatMap(buffetResumo -> buffetResumo.getTiposEventos().stream()
                        .map(tipoEvento -> Map.entry(tipoEvento.getDescricao(), buffetResumo)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        return buffetsPorTipoEvento;
    }

    public List<Buffet> getBuffetPorPesquisaNome(String q) {
        return buffetRepository.findByNomeContainingIgnoreCase(q);
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

    public List<BuffetResumoDto> pegarBuffetsProprietario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(404, "Usuário não encontrado na base de dados", null));

        List<Buffet> buffets = buffetRepository.findBuffetByUsuario(usuario);

        return buffets.stream().map(buffetMapper::toResumoDto).toList();
    }
}
