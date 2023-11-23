package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.buffet.BuffetResumoDto;
import eventify.api_spring.dto.buffet.BuffetSmartSyncResumoDto;
import eventify.api_spring.dto.dashboard.AvaliacaoDto;
import eventify.api_spring.dto.dashboard.DadosFinanceiroDto;
import eventify.api_spring.dto.dashboard.KpiEventifyUnificadoDto;
import eventify.api_spring.dto.dashboard.MovimentacaoFinanceiraDto;
import eventify.api_spring.dto.dashboard.RendaMesAnteriorDto;
import eventify.api_spring.dto.dashboard.RendaMesAtualDto;
import eventify.api_spring.dto.dashboard.RendaMesDto;
import eventify.api_spring.dto.dashboard.TaxaAbandonoDto;
import eventify.api_spring.dto.dashboard.TaxaSatisfacaoDto;
import eventify.api_spring.dto.evento.EventoOrcamentoDto;
import eventify.api_spring.dto.buffet.BuffetPublicoDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.smartsync.AcessoDto;
import eventify.api_spring.dto.smartsync.AtividadeDto;
import eventify.api_spring.dto.smartsync.AvaliacaoBaseadoEvento;
import eventify.api_spring.dto.smartsync.ContratoDto;
import eventify.api_spring.dto.smartsync.ImpressaoDto;
import eventify.api_spring.dto.smartsync.InfoEventoDto;
import eventify.api_spring.dto.smartsync.InfoFinanceiroEventoDto;
import eventify.api_spring.dto.smartsync.InfoStatusDto;
import eventify.api_spring.dto.smartsync.KpiUnificadoDto;
import eventify.api_spring.dto.smartsync.RendaDto;
import eventify.api_spring.dto.smartsync.RendaRetornoDto;
import eventify.api_spring.dto.smartsync.TarefaEventoProximoDto;
import eventify.api_spring.dto.smartsync.TransacaoDto;
import eventify.api_spring.dto.smartsync.VisaoGeralMensalDto;
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
import java.math.BigDecimal;
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

    private Usuario findUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados"));

        if (usuario.getTipoUsuario() != 2) {
            throw new UnauthorizedException("Usuário não é um proprietário");
        }

        return usuario;
    }

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
        List<BuffetRespostaDto> buffets = buffetRepository.findByIsVisivelTrue().stream()
                .map(buffetMapper::toRespostaDto).toList();

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets disponíveis");
        }

        return buffets;
    }

    public BuffetRespostaDto buscarBuffetPorIdResposta(Integer id) {
        Buffet buffet = buscarBuffetPorId(id);

        if (Objects.isNull(buffet)) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        return buffetMapper.toRespostaDto(buffet);
    }

    public BuffetRespostaDto buscarBuffetPublicoPorIdResposta(Integer id) {
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

            if (Objects.isNull(buffetBanco.getEndereco()) && Objects.nonNull(buffet.getEndereco())) {
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

            if (buffetBanco.getAgendas().isEmpty()) {
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
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

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
        Usuario usuario = findUsuario(idUsuario);

        List<Buffet> buffets = buffetRepository.findBuffetByUsuario(usuario);

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        return buffets.stream().map(buffetMapper::toResumoDto).toList();
    }

    public List<BuffetSmartSyncResumoDto> buffetsPorIdUsuarioSmart(Integer idUsuario) {
        Usuario usuario = findUsuario(idUsuario);

        List<Buffet> buffets = buffetRepository.findBuffetByUsuario(usuario);

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        return buffets.stream().map(buffetMapper::toBuffetSmartSyncResumo).toList();
    }

    public TaxaAbandonoDto taxaAbandono(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery(
                "SELECT id_buffet, abandonos, total FROM vw_kpi_abandono_reserva WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();

        return new TaxaAbandonoDto(result[0], result[1], result[2]);
    }

    public TaxaSatisfacaoDto taxaSatisfacao(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager
                .createNativeQuery("SELECT media, total FROM vw_kpi_satisfacao WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();

        return new TaxaSatisfacaoDto(result[0], result[1]);
    }

    public MovimentacaoFinanceiraDto movimentacaoFinanceira(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery(
                "SELECT movimentacao, total FROM vw_kpi_movimentacao_financeira WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        Object[] result = (Object[]) query.getSingleResult();

        return new MovimentacaoFinanceiraDto(result[0], result[1]);
    }

    public List<DadosFinanceiroDto> dadosFinanceiro(Integer idBuffet) {
        Optional<Buffet> buffetOpt = buffetRepository.findById(idBuffet);

        if (buffetOpt.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        Query query = entityManager.createNativeQuery(
                "SELECT mes, qtd_eventos, faturamento FROM vw_dados_do_buffet WHERE id_buffet = :idBuffet");
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

        Query query = entityManager.createNativeQuery(
                "SELECT nome, nota, avaliacao, data FROM vw_avaliacoes_buffet_usuario WHERE id_buffet = :idBuffet");
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

        return atividadesDto.subList(0,20);
    }

    public Double calcularTaxaCrescimento(Double mesAtual, Double mesAnterior) {
        return (Double) ((mesAtual - mesAnterior) / mesAnterior) * 100;
    }

    public ImpressaoDto consultarImpressoes(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<AcessoDto> acessosDto = new ArrayList<>();

        Query queryAcessos = entityManager
                .createNativeQuery("SELECT * FROM vw_acessos_buffet WHERE id_buffet = :idBuffet");
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

        List<Double> indiceAcessos = acessosDto.stream().map(acesso -> (double) acesso.getQtdAcessos())
                .collect(Collectors.toList());
        Double indiceAcesso = calcularTaxaCrescimento(indiceAcessos.get(indiceAcessos.size() - 1),
                indiceAcessos.get(indiceAcessos.size() - 2));
        List<VisualizacaoDto> visualizacoesDto = new ArrayList<>();

        Query queryVisualizacoes = entityManager
                .createNativeQuery("SELECT * FROM vw_visualizacoes_buffet WHERE id_buffet = :idBuffet");
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

        List<Double> indiceVisualizacoes = visualizacoesDto.stream()
                .map(visualizacao -> (double) visualizacao.getQtdVisualizacoes()).collect(Collectors.toList());
        Double indiceVisualizacao = calcularTaxaCrescimento(indiceVisualizacoes.get(indiceVisualizacoes.size() - 1),
                indiceVisualizacoes.get(indiceVisualizacoes.size() - 2));

        List<eventify.api_spring.dto.smartsync.AvaliacaoDto> avaliacoesDto = new ArrayList<>();

        Query query = entityManager.createNativeQuery("SELECT * FROM vw_avaliacoes_buffet_smart_sync WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> avalicoes = query.getResultList();

        for (Object[] avaliacao : avalicoes) {
            String ano = (String) avaliacao[1];
            String mes = (String) avaliacao[2];
            Long quantidade = (Long) avaliacao[4];

            avaliacoesDto.add(new eventify.api_spring.dto.smartsync.AvaliacaoDto(ano, mes, quantidade));
        }

        List<Double> indiceAvaliacoes = avaliacoesDto.stream().map(avaliacao -> (double) avaliacao.getQtdAvaliacoes())
                .collect(Collectors.toList());
        Double indiceAvaliacao = calcularTaxaCrescimento(indiceAvaliacoes.get(indiceAvaliacoes.size() - 1),
                indiceAvaliacoes.get(indiceAvaliacoes.size() - 2));

        return new ImpressaoDto(acessosDto, indiceAcesso, visualizacoesDto, indiceVisualizacao, avaliacoesDto,
                indiceAvaliacao);
    }

    public AvaliacaoBaseadoEvento consultarAvaliacaoBaseadoEventos(Integer id) {
        Optional<Buffet> buffet = buffetRepository.findById(id);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<Double> notas = eventoRepository.findNotaByBuffet(buffet.get());
        notas.removeIf(Objects::isNull);

        if (notas.isEmpty()) {
            throw new NoContentException("Não há notas");
        }

        Double notaMedia = 0.0;

        for (Double nota : notas) {
            notaMedia += nota;
        }

        notaMedia = notaMedia / notas.size();

        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate mesAnterior = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesAnterior = mesAtual.minusDays(1);

        Query queryMesAtual = entityManager.createNativeQuery(
                "SELECT nota FROM evento WHERE id_buffet = :idBuffet AND data BETWEEN :mesAtual AND :agora AND status = 6");
        queryMesAtual.setParameter("idBuffet", id);
        queryMesAtual.setParameter("mesAtual", mesAtual);
        queryMesAtual.setParameter("agora", LocalDateTime.now());
        List<Double> notasAtual = queryMesAtual.getResultList();
        notasAtual.removeIf(Objects::isNull);

        Query queryMesAnterior = entityManager.createNativeQuery(
                "SELECT nota FROM evento WHERE id_buffet = :idBuffet AND data < :ultimoDiaMesAnterior AND status = 6");
        queryMesAnterior.setParameter("idBuffet", id);
        queryMesAnterior.setParameter("ultimoDiaMesAnterior", ultimoDiaMesAnterior);
        List<Double> notasAnterior = queryMesAnterior.getResultList();
        notasAnterior.removeIf(Objects::isNull);

        Double notaMediaMesAtual = 0.0;
        Double notaMediaMesAnterior = 0.0;

        for (Double nota : notasAtual) {
            notaMediaMesAtual += nota;
        }

        for (Double nota : notasAnterior) {
            notaMediaMesAnterior += nota;
        }

        notaMediaMesAtual = notaMediaMesAtual / notasAtual.size();
        notaMediaMesAnterior = notaMediaMesAnterior / notasAnterior.size();

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
            Integer id = (Integer) info[0];
            String nome = (String) info[1];
            String cpf = (String) info[2];
            String email = (String) info[3];
            Timestamp data = (Timestamp) info[4];
            BigDecimal precoBigDecimal = (BigDecimal) info[5];
            Double preco = precoBigDecimal.doubleValue();

            String status = (String) info[6];
            Timestamp dataPedido = (Timestamp) info[7];

            infosDto.add(new InfoEventoDto(id, nome, cpf, email, data, preco, status, dataPedido));
        }

        return infosDto;
    }

    @Transactional
    public List<TransacaoDto> consultarInfoTransacoes(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<TransacaoDto> ocorrencias = new ArrayList<>();
        List<Object[]> transacoes = buffetRepository.spTransacoes(idBuffet);

        for (Object[] transacao : transacoes) {
            String pagante = (String) transacao[0];
            String cpf = (String) transacao[1];
            String email = (String) transacao[2];
            BigDecimal valor = (BigDecimal) transacao[3];
            Long isGastoLong = (Long) transacao[4];
            String motivo = (String) transacao[5];
            String data = (String) transacao[6];

            Integer is_gasto = isGastoLong.intValue();

            ocorrencias.add(new TransacaoDto(pagante, cpf, email, valor.doubleValue(), is_gasto, motivo, data));
        }

        return ocorrencias;
    }

    public RendaRetornoDto compararRendaMesAnteriorAtual(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate mesAnterior = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesAnterior = mesAtual.minusDays(1);
        LocalDate ultimoDiaMesCorrente = mesAtual.withDayOfMonth(mesAtual.lengthOfMonth());

        Query queryMesAtual = entityManager.createNativeQuery(
                "SELECT c.nome, e.data, e.preco FROM evento e JOIN usuario c ON c.id = e.id_contratante WHERE e.id_buffet = :idBuffet AND data BETWEEN :mesAtual AND :ultimoDiaMesAtual AND status IN (5, 6) ;");
        queryMesAtual.setParameter("idBuffet", idBuffet);
        queryMesAtual.setParameter("mesAtual", mesAtual);
        queryMesAtual.setParameter("ultimoDiaMesAtual", ultimoDiaMesCorrente);
        List<Object[]> rendasAtual = queryMesAtual.getResultList();

        if (rendasAtual.isEmpty()) {
            throw new NoContentException("Não há rendas no mês atual");
        }

        List<RendaDto> rendaAtualDto = new ArrayList<>();
        for (Object[] rendaAtual : rendasAtual) {
            String nome = (String) rendaAtual[0];
            Timestamp data = (Timestamp) rendaAtual[1];
            BigDecimal precoBigDecimal = (BigDecimal) rendaAtual[2];
            Double preco = precoBigDecimal.doubleValue();

            rendaAtualDto.add(new RendaDto(nome, data, preco));
        }

        Query queryMesAnterior = entityManager.createNativeQuery(
                "SELECT c.nome, e.data, e.preco FROM evento e JOIN usuario c ON c.id = e.id_contratante WHERE e.id_buffet = :idBuffet AND data BETWEEN :mesAnterior AND :ultimoDiaMesAnterior AND status IN (5, 6) ;");
        queryMesAnterior.setParameter("idBuffet", idBuffet);
        queryMesAnterior.setParameter("mesAnterior", mesAnterior);
        queryMesAnterior.setParameter("ultimoDiaMesAnterior", ultimoDiaMesAnterior);
        List<Object[]> rendasAnterior = queryMesAnterior.getResultList();

        if (rendasAnterior.isEmpty()) {
            throw new NoContentException("Não há rendas no mês anterior");
        }

        List<RendaDto> rendaAnteriorDto = new ArrayList<>();
        for (Object[] rendaAnterior : rendasAnterior) {
            String nome = (String) rendaAnterior[0];
            Timestamp data = (Timestamp) rendaAnterior[1];
            BigDecimal precoBigDecimal = (BigDecimal) rendaAnterior[2];
            Double preco = precoBigDecimal.doubleValue();

            rendaAnteriorDto.add(new RendaDto(nome, data, preco));
        }

        Calendar calendario = Calendar.getInstance();

        Integer ultimoDiaMesAtual = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendario.add(Calendar.MONTH, -1);
        Integer ultimoDiaMesPassado = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<RendaDto> rendaAtualComDiasVazios = new ArrayList<>();

        for (Integer i = 1; i <= ultimoDiaMesAtual; i++) {
            LocalDate dataRetorno = LocalDate.now().withDayOfMonth(i);
            Long data = LocalDate.now().withDayOfMonth(i).atStartOfDay().toLocalDate().toEpochDay();
            Boolean isUsed = false;

            for (RendaDto rendaDto : rendaAtualDto) {
                LocalDate rendaData = Timestamp
                        .valueOf(rendaDto.getData().toLocalDateTime().toLocalDate().atTime(0, 0, 0, 0))
                        .toLocalDateTime().toLocalDate();
                Long rendaDataEpoch = rendaData.toEpochDay();

                if (data.equals(rendaDataEpoch)) {
                    rendaDto.setData(
                            Timestamp.valueOf(rendaDto.getData().toLocalDateTime().toLocalDate().atTime(0, 0, 0, 0)));
                    rendaAtualComDiasVazios.add(rendaDto);

                    isUsed = true;
                    break;
                }
            }

            if (!isUsed) {
                rendaAtualComDiasVazios
                        .add(new RendaDto("N/A", Timestamp.valueOf(dataRetorno.atTime(0, 0, 0, 0)), 0.0));
            }
        }

        List<RendaDto> rendaAnteriorComDiasVazios = new ArrayList<>();

        for (Integer j = 1; j <= ultimoDiaMesPassado; j++) {
            LocalDate dataRetorno = LocalDate.now().minusMonths(1).withDayOfMonth(j);
            Long data = LocalDate.now().minusMonths(1).withDayOfMonth(j).atStartOfDay().toLocalDate().toEpochDay();
            Boolean isUsed = false;

            for (RendaDto rendaDto : rendaAnteriorDto) {
                LocalDate rendaData = Timestamp
                        .valueOf(rendaDto.getData().toLocalDateTime().toLocalDate().atTime(0, 0, 0, 0))
                        .toLocalDateTime().toLocalDate();
                Long rendaDataEpoch = rendaData.toEpochDay();

                if (data.equals(rendaDataEpoch)) {
                    rendaDto.setData(
                            Timestamp.valueOf(rendaDto.getData().toLocalDateTime().toLocalDate().atTime(0, 0, 0, 0)));
                    rendaAnteriorComDiasVazios.add(rendaDto);

                    isUsed = true;
                    break;
                }
            }

            if (!isUsed) {
                rendaAnteriorComDiasVazios
                        .add(new RendaDto("N/A", Timestamp.valueOf(dataRetorno.atTime(0, 0, 0, 0)), 0.0));
            }
        }

        return new RendaRetornoDto(rendaAnteriorComDiasVazios, rendaAtualComDiasVazios);
    }

    public VisaoGeralMensalDto consultarVisaoGeralMensal(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        LocalDate mesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate mesAnterior = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesAtual = mesAtual.minusDays(1);

        Query queryRealizados = entityManager.createNativeQuery(
                "SELECT c.nome, e.data, e.preco FROM evento e JOIN usuario c ON c.id = e.id_contratante WHERE e.id_buffet = :idBuffet AND data BETWEEN :mesAtual AND :agora AND status = 6");
        queryRealizados.setParameter("idBuffet", idBuffet);
        queryRealizados.setParameter("mesAtual", mesAtual);
        queryRealizados.setParameter("agora", LocalDateTime.now());
        List<Object[]> realizados = queryRealizados.getResultList();

        if (realizados.isEmpty()) {
            throw new NoContentException("Não há eventos realizados no mês atual");
        }

        List<InfoFinanceiroEventoDto> realizadosDto = new ArrayList<>();
        for (Object[] realizado : realizados) {
            String nome = (String) realizado[0];
            Timestamp data = (Timestamp) realizado[1];
            BigDecimal precoBigDecimal = (BigDecimal) realizado[2];
            Double preco = precoBigDecimal.doubleValue();

            realizadosDto.add(new InfoFinanceiroEventoDto(nome, data, preco));
        }

        Query queryProximos = entityManager.createNativeQuery(
                "SELECT c.nome, e.data, e.preco FROM evento e JOIN usuario c ON c.id = e.id_contratante WHERE e.id_buffet = :idBuffet AND data BETWEEN NOW() AND LAST_DAY(NOW()) AND status = 5");
        queryProximos.setParameter("idBuffet", idBuffet);
        List<Object[]> proximos = queryProximos.getResultList();

        if (proximos.isEmpty()) {
            throw new NoContentException("Não há eventos próximos no mês atual");
        }

        List<InfoFinanceiroEventoDto> proximosDto = new ArrayList<>();
        for (Object[] proximo : proximos) {
            String nome = (String) proximo[0];
            Timestamp data = (Timestamp) proximo[1];
            BigDecimal precoBigDecimal = (BigDecimal) proximo[2];
            Double preco = precoBigDecimal.doubleValue();

            proximosDto.add(new InfoFinanceiroEventoDto(nome, data, preco));
        }

        return new VisaoGeralMensalDto(realizadosDto, proximosDto);
    }

    public Integer consultarQuantidadeEventos(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager
                .createNativeQuery("SELECT COUNT(*) FROM evento WHERE id_buffet = :idBuffet AND status = 6");
        query.setParameter("idBuffet", idBuffet);
        Long quantidade = (Long) query.getSingleResult();

        return quantidade.intValue();
    }

    public Double consultarRendaMediaEventos(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager
                .createNativeQuery("SELECT AVG(preco) FROM evento WHERE id_buffet = :idBuffet AND status = 6");
        query.setParameter("idBuffet", idBuffet);
        BigDecimal media = (BigDecimal) query.getSingleResult();

        return media.doubleValue();
    }

    public Double consultarRendaTotal(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager
                .createNativeQuery("SELECT SUM(preco) FROM evento WHERE id_buffet = :idBuffet AND status = 6");
        query.setParameter("idBuffet", idBuffet);
        BigDecimal total = (BigDecimal) query.getSingleResult();

        return total.doubleValue();
    }

    @Transactional
    public Double consultarGastoTotal(Integer idBuffet) {
        List<TransacaoDto> transacoes = this.consultarInfoTransacoes(idBuffet);
        Double gasto = 0.0;

        for (TransacaoDto transacao : transacoes) {
            if (transacao.getIsPago() == 1) {
                gasto += transacao.getValor();
            }
        }

        return gasto;
    }

    public List<TarefaEventoProximoDto> consultarTarefasProximas(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager.createNativeQuery("SELECT * FROM vw_tarefas_proximas WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> tarefas = query.getResultList();

        List<TarefaEventoProximoDto> tarefasDto = new ArrayList<>();
        for (Object[] tarefa : tarefas) {
            Integer idTarefa = (Integer) tarefa[0];
            String nomeContratante = (String) tarefa[6];
            Timestamp dataEvento = (Timestamp) tarefa[5];
            String nomeTarefa = (String) tarefa[2];
            String descricaoTarefa = (String) tarefa[3];
            List<String> responsaveisTarefa = new ArrayList<>();
            Date dataTarefa = (Date) tarefa[4];
            Timestamp timestampDataTarefa = new Timestamp(dataTarefa.getTime());

            tarefasDto.add(new TarefaEventoProximoDto(idTarefa, nomeContratante, dataEvento, nomeTarefa,
                    descricaoTarefa, responsaveisTarefa, timestampDataTarefa));
        }

        for (TarefaEventoProximoDto tarefa : tarefasDto) {
            Integer idTarefa = tarefa.getIdTarefa();

            Query queryResponsaveis = entityManager.createNativeQuery(
                    "SELECT id_tarefa, nome_executor FROM vw_tarefas_proximas_responsaveis WHERE id_tarefa = :idTarefa");
            queryResponsaveis.setParameter("idTarefa", idTarefa);
            List<Object[]> responsaveis = queryResponsaveis.getResultList();

            for (Object[] responsavel : responsaveis) {
                String nomeResponsavel = (String) responsavel[1];

                tarefa.getResponsaveisTarefa().add(nomeResponsavel);
            }
        }

        return tarefasDto;
    }

    @Transactional
    public List<InfoStatusDto> consultarInfoStatus(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        List<InfoStatusDto> infos = new ArrayList<>();
        List<Object[]> status = buffetRepository.spInfoStatus(idBuffet);

        for (Object[] info : status) {
            Integer stat = (Integer) info[0];
            Long quantidade = (Long) info[1];
            String statusTraduzido = (String) info[2];

            infos.add(new InfoStatusDto(stat, quantidade.intValue(), statusTraduzido));
        }

        // Continuar aqui

        return infos;
    }

    public List<ContratoDto> consultarContratos(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado");
        }

        Query query = entityManager.createNativeQuery("SELECT * FROM vw_contratos WHERE id_buffet = :idBuffet");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> contratos = query.getResultList();

        List<ContratoDto> contratosDto = new ArrayList<>();
        for (Object[] contrato : contratos) {
            Integer id = (Integer) contrato[0];
            String nomeContratante = (String) contrato[1];
            BigDecimal precoBigDecimal = (BigDecimal) contrato[2];
            Double preco = precoBigDecimal.doubleValue();
            Timestamp data = (Timestamp) contrato[3];
            Integer descricaoTarefa = (Integer) contrato[4];
 
            contratosDto.add(new ContratoDto(id, nomeContratante, preco, data, descricaoTarefa, ""));
        }

        return contratosDto;
    }

    public KpiUnificadoDto consultarKpis(Integer idBuffet) {
        Integer quantidadeEventos = this.consultarQuantidadeEventos(idBuffet);
        Double media = this.consultarRendaMediaEventos(idBuffet);
        Double total = this.consultarRendaTotal(idBuffet);
        Double gasto = this.consultarGastoTotal(idBuffet);

        return new KpiUnificadoDto(quantidadeEventos, media, total, gasto);
    }

    public KpiEventifyUnificadoDto consultarKpisEventify() {
        KpiEventifyUnificadoDto kpis = new KpiEventifyUnificadoDto();

        Query QueryRendaAtual = entityManager
                .createNativeQuery("SELECT SUM(preco) valor, TRADUZ_MES(MONTHNAME(data)) mes\n" + //
                        "FROM evento \n" + //
                        "WHERE status = 6 \n" + //
                        "  AND YEAR(data) = YEAR(CURRENT_DATE()) \n" + //
                        "  AND MONTH(data) = MONTH(CURRENT_DATE());");
        List<Object[]> rendaAtual = QueryRendaAtual.getResultList();

        if (rendaAtual.isEmpty()) {
            throw new NotFoundException("Não há renda para mostrar");
        } else {
            Object[] rendaAtualObj = rendaAtual.get(0);
            String mes = (String) rendaAtualObj[1];
            BigDecimal rendaAtualBigDecimal = (BigDecimal) rendaAtualObj[0];
            Double rendaAtualDouble = rendaAtualBigDecimal.doubleValue();

            kpis.setRendaMesAtual(new RendaMesAtualDto(mes, rendaAtualDouble));
        }

        Query QueryRendaAnterior = entityManager
                .createNativeQuery("SELECT SUM(preco) valor, TRADUZ_MES(MONTHNAME(data)) mes\n" + //
                        "FROM evento  \n" + //
                        "WHERE status = 6 \n" + //
                        "AND YEAR(data) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) \n" + //
                        "AND MONTH(data) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH);");
        List<Object[]> rendaAnterior = QueryRendaAnterior.getResultList();

        if (rendaAnterior.isEmpty()) {
            throw new NotFoundException("Não há renda para mostrar");
        } else {
            Object[] rendaAnteriorObj = rendaAnterior.get(0);
            String mes = (String) rendaAnteriorObj[1];
            BigDecimal rendaAnteriorBigDecimal = (BigDecimal) rendaAnteriorObj[0];
            Double rendaAnteriorDouble = rendaAnteriorBigDecimal.doubleValue();

            kpis.setRendaMesAnterior(new RendaMesAnteriorDto(mes, rendaAnteriorDouble));
        }

        Query queryRendaTotal = entityManager
                .createNativeQuery("SELECT SUM(preco) FROM eventify.evento WHERE status = 6;");
        BigDecimal rendaTotalBigDecimal = (BigDecimal) queryRendaTotal.getSingleResult();

        if (rendaTotalBigDecimal == null) {
            throw new NotFoundException("Não há renda para mostrar");
        } else {
            Double rendaTotalDouble = rendaTotalBigDecimal.doubleValue();
            kpis.setRendaTotal(rendaTotalDouble);
        }

        return kpis;
    }

    public List<RendaMesDto> consultarRendaMesEventify() {
        List<RendaMesDto> rendas = new ArrayList<>();

        Query query = entityManager.createNativeQuery("SELECT * FROM vw_comparar_seis_meses;");
        List<Object[]> rendasObj = query.getResultList();

        if (rendasObj.isEmpty()) {
            throw new NotFoundException("Não há renda para mostrar");
        } else {
            for (Object[] rendaObj : rendasObj) {
                String mes = (String) rendaObj[0];
                BigDecimal rendaBigDecimal = (BigDecimal) rendaObj[1];
                Double rendaDouble = rendaBigDecimal.doubleValue();

                rendas.add(new RendaMesDto(mes, rendaDouble));
            }
        }

        // Fazendo dessa maneira pois o Collections.reverse não supre a necesidade e looping consumiria mais memória
        List<RendaMesDto> rendasOrdenada = new ArrayList<>();
        rendasOrdenada.add(rendas.get(5));
        rendasOrdenada.add(rendas.get(4));
        rendasOrdenada.add(rendas.get(3));
        rendasOrdenada.add(rendas.get(2));
        rendasOrdenada.add(rendas.get(1));
        rendasOrdenada.add(rendas.get(0));

        return rendasOrdenada;
    }

}