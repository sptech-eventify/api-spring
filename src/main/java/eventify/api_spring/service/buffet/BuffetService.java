package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.domain.evento.Evento;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.buffet.BuffetSmartSyncResumoDto;
import eventify.api_spring.dto.dashboard.AvaliacaoDto;
import eventify.api_spring.dto.dashboard.DadosFinanceiroDto;
import eventify.api_spring.dto.dashboard.MovimentacaoFinanceiraDto;
import eventify.api_spring.dto.dashboard.TaxaAbandonoDto;
import eventify.api_spring.dto.dashboard.TaxaSatisfacaoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.smartsync.AcessoDto;
import eventify.api_spring.dto.smartsync.AtividadeDto;
import eventify.api_spring.dto.smartsync.AvaliacaoBaseadoEvento;
import eventify.api_spring.dto.smartsync.ImpressaoDto;
import eventify.api_spring.dto.smartsync.InfoEventoDto;
import eventify.api_spring.dto.smartsync.VisualizacaoDto;
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
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.sql.Time;
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
                buffetBanco.setVisivel(false);
                buffetRepository.save(buffetBanco);
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

    public BuffetSmartSyncResumoDto resumoBuffet(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        return buffetMapper.toBuffetSmartSyncResumo(buffet.get());
    }

    @Transactional
    public List<AtividadeDto> consultarAtividades(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }
        
        List<Object[]> atividades = buffetRepository.spAtividades(idBuffet);

        List<AtividadeDto> atividadesDto = new ArrayList<>();

        for (Object[] atividade : atividades) {
            Character id = (Character) atividade[0];
            String nome = (String) atividade[1];
            String descricao = (String) atividade[2];
            String data = (String) atividade[3];

            atividadesDto.add(new AtividadeDto(id, nome, descricao, data));
        }

        if (atividadesDto.isEmpty()) {
            throw new NoContentException("Não há atividades");
        }

        return atividadesDto;
    }

    public Double calcularTaxaCrescimento(Double mesAtual, Double mesAnterior) {
        return (Double)((mesAtual - mesAnterior) / mesAnterior) * 100;
    }

    public ImpressaoDto consultarImpressoes(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<AcessoDto> acessosDto = new ArrayList<>();
        
        Query queryAcessos = entityManager.createNativeQuery("SELECT * FROM vw_acessos_buffet WHERE id_buffet = :idBuffet");
        queryAcessos.setParameter("idBuffet", idBuffet);
        List<Object[]> acessos = queryAcessos.getResultList();

        for (Object[] acesso : acessos) {
            Integer id = (Integer) acesso[0];
            String ano_s = (String) acesso[1]; 
            Integer ano = Integer.parseInt(ano_s);
            String mes = (String) acesso[2];
            Long qtdAcessos = (Long) acesso[4];

            acessosDto.add(new AcessoDto(id, ano, mes, qtdAcessos));
        }

        List<Double> indiceAcessos = acessosDto.stream().map(acesso -> (double) acesso.getQtdAcessos()).collect(Collectors.toList());
        Double indiceAcesso = calcularTaxaCrescimento(indiceAcessos.get(indiceAcessos.size() - 1),  indiceAcessos.get(indiceAcessos.size() - 2));

        List<VisualizacaoDto> visualizacoesDto = new ArrayList<>();

        Query queryVisualizacoes = entityManager.createNativeQuery("SELECT * FROM vw_visualizacoes_buffet WHERE id_buffet = :idBuffet");
        queryVisualizacoes.setParameter("idBuffet", idBuffet);
        List<Object[]> visualizacoes = queryVisualizacoes.getResultList();

        for (Object[] visualizacao : visualizacoes) {
            Integer id = (Integer) visualizacao[0];
            String ano_s = (String) visualizacao[1]; 
            Integer ano = Integer.parseInt(ano_s);            
            String mes = (String) visualizacao[2];
            Long qtdVisualizacoes = (Long) visualizacao[4];

            visualizacoesDto.add(new VisualizacaoDto(id, ano, mes, qtdVisualizacoes));
        }

        List<Double> indiceVisualizacoes = visualizacoesDto.stream().map(visualizacao -> (double) visualizacao.getQtdVisualizacoes()).collect(Collectors.toList());
        Double indiceVisualizacao = calcularTaxaCrescimento(indiceVisualizacoes.get(indiceVisualizacoes.size() - 1),  indiceVisualizacoes.get(indiceVisualizacoes.size() - 2));


        List<eventify.api_spring.dto.smartsync.AvaliacaoDto> avaliacoesDto = new ArrayList<>();
        
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_avaliacoes_buffet WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> avalicoes = query.getResultList();

        for (Object[] avaliacao : avalicoes) {
            Integer id = (Integer) avaliacao[0];
            String ano_s = (String) avaliacao[1]; 
            Integer ano = Integer.parseInt(ano_s);   
            String mes = (String) avaliacao[2];
            Long qtdAvaliacoes = (Long) avaliacao[4];

            avaliacoesDto.add(new eventify.api_spring.dto.smartsync.AvaliacaoDto(id, ano, mes, qtdAvaliacoes));
        }

        List<Double> indiceAvaliacoes = avaliacoesDto.stream().map(avaliacao -> (double) avaliacao.getQtdAvaliacao()).collect(Collectors.toList());
        Double indiceAvaliacao = calcularTaxaCrescimento(indiceAvaliacoes.get(indiceAvaliacoes.size() - 1),  indiceAvaliacoes.get(indiceAvaliacoes.size() - 2));

        return new ImpressaoDto(acessosDto, indiceAcesso, visualizacoesDto, indiceVisualizacao, avaliacoesDto, indiceAvaliacao);     
    }

    public AvaliacaoBaseadoEvento consultarAvaliacaoBaseadoEventos(Integer id) {
        Optional<Buffet> buffet = buffetRepository.findById(id);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<Double> notas = eventoRepository.findNotaByBuffet(buffet.get());

        if (notas.isEmpty()) {
            throw new NoContentException("Não há notas");
        }

        Double notaMedia = notas.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate mesAnterior = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesAnterior = mesAtual.minusDays(1);

        Query queryMesAtual = entityManager.createNativeQuery("SELECT nota FROM evento WHERE id_buffet = :idBuffet AND data BETWEEN :mesAtual AND :agora AND status = 6");
        queryMesAtual.setParameter("idBuffet", id);
        queryMesAtual.setParameter("mesAtual", mesAtual);
        queryMesAtual.setParameter("agora", LocalDateTime.now());
        List<Double> notasAtual = queryMesAtual.getResultList();

        Query queryMesAnterior = entityManager.createNativeQuery("SELECT nota FROM evento WHERE id_buffet = :idBuffet AND data < :ultimoDiaMesAnterior AND status = 6");
        queryMesAnterior.setParameter("idBuffet", id);
        queryMesAnterior.setParameter("ultimoDiaMesAnterior", ultimoDiaMesAnterior);
        List<Double> notasAnterior = queryMesAnterior.getResultList();

        Double notaMediaMesAtual = notasAtual.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        Double notaMediaMesAnterior = notasAnterior.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        Double indice = calcularTaxaCrescimento(notaMediaMesAtual, notaMediaMesAnterior);

        return new AvaliacaoBaseadoEvento(notaMedia, indice);
    }

    public List<InfoEventoDto> consultarInfoEventos(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager.createNativeQuery("SELECT * FROM vw_info_eventos WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> infos = query.getResultList();

        List<InfoEventoDto> infosDto = new ArrayList<>();
        for (Object[] info : infos) {
            String nome = (String) info[1];
            String cpf = (String) info[2];
            String email = (String) info[3];
            Timestamp data = (Timestamp) info[4];
            String status = (String) info[5];
            Timestamp dataPedido = (Timestamp) info[6];

            infosDto.add(new InfoEventoDto(nome, cpf, email, data, status, dataPedido));
        }

        return infosDto;
    }
}