package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.dashboard.AvaliacaoDto;
import eventify.api_spring.dto.dashboard.DadosFinanceiroDto;
import eventify.api_spring.dto.dashboard.MovimentacaoFinanceiraDto;
import eventify.api_spring.dto.dashboard.TaxaAbandonoDto;
import eventify.api_spring.dto.dashboard.TaxaSatisfacaoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.utils.DataDto;
import eventify.api_spring.exception.http.ConflictException;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.exception.http.UnauthorizedException;
import eventify.api_spring.mapper.buffet.BuffetMapper;
import eventify.api_spring.mapper.buffet.ImagemMapper;
import eventify.api_spring.mapper.utils.DataMapper;
import eventify.api_spring.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        endereco.setDataCriacao(LocalDateTime.now());
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

                endereco.setDataCriacao(LocalDateTime.now());
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

        Map<String, List<BuffetResumoDto>> buffets = buffetsResumo.stream()
                .flatMap(buffetResumo -> buffetResumo.getTiposEventos().stream()
                        .map(tipoEvento -> Map.entry(tipoEvento.getDescricao(), buffetResumo)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        return buffets;
    }

    public List<DataDto> datasOcupadas(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isPresent()) {
            List<LocalDate> datas = eventoRepository.findAllDataByBuffet(buffetOpt.get());

            if (datas.isEmpty()) {
                throw new NoContentException("Não há datas ocupadas");
            }

            List<DataDto> datasDto = datas.stream().map(DataMapper::toDto).toList();

            return datasDto;
        }

        throw new NotFoundException("Buffet não encontrado na base de dados");
    }

    public List<EventoOrcamentoDto> orcamentosPorIdBuffet(Integer idBuffet) {
        Buffet buffet = buffetRepository.findByIsVisivelTrueAndId(idBuffet);

        if (Objects.nonNull(buffet)) {
            List<EventoOrcamentoDto> orcamentos = eventoRepository.findAllOrcamentosByBuffetPublico(buffet);
            
            if (orcamentos.isEmpty()) {
                throw new NoContentException("Não há orçamentos");
            }

            return orcamentos;
        }
        
        throw new NotFoundException("Buffet não encontrado na base de dados");
    }

    public List<BuffetResumoDto> buffetsPorIdUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados"));

        if (usuario.getTipoUsuario() != 2) {
            throw new UnauthorizedException("Usuário não é um proprietário");
        }

        List<Buffet> buffets = buffetRepository.findBuffetByUsuario(usuario);

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        return buffets.stream().map(buffetMapper::toResumoDto).toList();
    }

    public TaxaAbandonoDto taxaAbandono(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery("SELECT id_buffet, abandonos, total FROM vw_kpi_abandono_reserva WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();
        
        return new TaxaAbandonoDto(result[0], result[1],  result[2]);
    }

    public TaxaSatisfacaoDto taxaSatisfacao(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery("SELECT media, total FROM vw_kpi_satisfacao WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();
        
        return new TaxaSatisfacaoDto(result[0], result[1]);
    }

    public MovimentacaoFinanceiraDto movimentacaoFinanceira(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery("SELECT movimentacao, total FROM vw_kpi_movimentacao_financeira WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();

        return new MovimentacaoFinanceiraDto(result[0], result[1]);
    }

    public List<DadosFinanceiroDto> dadosFinanceiro(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);
    
        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }
        
        Query query = entityManager.createNativeQuery("SELECT mes, qtd_eventos, faturamento FROM vw_dados_do_buffet WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> resultList = query.getResultList();
    
        List<DadosFinanceiroDto> dadosFinanceiroList = new ArrayList<>();
        for (Object[] result : resultList) {
            Object mes = result[0];
            Object qtdEventos = result[1];
            Object faturamento = result[2];
    
            dadosFinanceiroList.add(new DadosFinanceiroDto(mes, qtdEventos, faturamento));
        }
    
        return dadosFinanceiroList;
    }

    public List<AvaliacaoDto> avaliacoes(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery("SELECT nome, nota, avaliacao, data FROM vw_avaliacoes_buffet WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> resultList = query.getResultList();

        List<AvaliacaoDto> avaliacoesList = new ArrayList<>();
        for (Object[] result : resultList) {
            Object nome = result[0];
            Object nota = result[1];
            Object avaliacao = result[2];
            Object data = result[3];
    
            avaliacoesList.add(new AvaliacaoDto(nome, nota, avaliacao, data));
        }

        return avaliacoesList;
    }

    public List<Buffet> getBuffetPorPesquisaNome(String q) {
        return buffetRepository.findByNomeContainingIgnoreCase(q);
    }
}
