package eventify.api_spring.service.smartsync;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.dto.smartsync.dashboard.BuffetInativoDto;
import eventify.api_spring.dto.smartsync.dashboard.CategoriaKpiDto;
import eventify.api_spring.dto.smartsync.dashboard.ContratanteKpi;
import eventify.api_spring.dto.smartsync.dashboard.EventoCanceladoDto;
import eventify.api_spring.dto.smartsync.dashboard.EventoProximoDto;
import eventify.api_spring.dto.smartsync.dashboard.FormularioDinamicoDto;
import eventify.api_spring.dto.smartsync.dashboard.KanbanStatusDto;
import eventify.api_spring.dto.smartsync.dashboard.ProprietarioKpiDto;
import eventify.api_spring.dto.smartsync.dashboard.RegistroDto;
import eventify.api_spring.dto.smartsync.dashboard.RegistroKpiDto;
import eventify.api_spring.dto.smartsync.dashboard.TelaKpis;
import eventify.api_spring.dto.smartsync.dashboard.UtlizacaoFormularioMensalDto;
import eventify.api_spring.exception.http.NoContentException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DashboardService {
    @Autowired
    private EntityManager entityManager;

    public List<EventoProximoDto> retornarListagemProximosEventos(Integer idBuffet) {
        String sql = "SELECT * FROM vw_listagem_proximos_eventos WHERE id_buffet = :idBuffet";
        List<Object[]> eventos = entityManager.createNativeQuery(sql).setParameter("idBuffet", idBuffet).getResultList();

        List<EventoProximoDto> eventosDto = new ArrayList();
        for (Object[] evento : eventos) {
            EventoProximoDto eventoDto = new EventoProximoDto();

            eventoDto.setCliente((String) evento[1]);
            eventoDto.setId((Integer) evento[2]);
            eventoDto.setData((java.sql.Timestamp) evento[3]);
            eventoDto.setStatus((Integer) evento[4]);

            Long tarefasRealizadas = (Long) evento[5];
            eventoDto.setTarefasRealizadas(tarefasRealizadas.intValue());
        
            Long tarefasPendentes = (Long) evento[6];
            eventoDto.setTarefasPendentes(tarefasPendentes.intValue());

            Long tarefasEmAndamento = (Long) evento[7];
            eventoDto.setTarefasEmAndamento(tarefasEmAndamento.intValue());

            eventosDto.add(eventoDto);
        }

        return eventosDto;
    }

    public List<KanbanStatusDto> retornarListagemDadosProximosEventos(Integer idBuffet) {
        Query queryTarefas = entityManager.createNativeQuery("SELECT * FROM vw_status_eventos WHERE id_buffet = :idBuffet");
        queryTarefas.setParameter("idBuffet", idBuffet);
        List<Object[]> eventos = queryTarefas.getResultList();

        List<KanbanStatusDto> kanbans = new ArrayList<>();
        for (Object[] evento : eventos) {
            Integer idEvento = (Integer) evento[1];
            String nomeCliente = (String) evento[2];
            LocalDate dataEvento = (Timestamp) evento[3] != null ? ((Timestamp) evento[3]).toLocalDateTime().toLocalDate() : null;
            LocalDate dataEstimada = (Date) evento[4] != null ? ((Date) evento[4]).toLocalDate() : null;
            Integer tarefasRealizadas = ((Long) evento[5]).intValue();
            Integer tarefasPendentes = ((Long) evento[6]).intValue();
            Integer tarefasEmAndamento = ((Long) evento[7]).intValue();
            Integer idServico = (Integer) evento[8];

            kanbans.add(new KanbanStatusDto(idEvento, nomeCliente, dataEvento, dataEstimada, tarefasEmAndamento, tarefasPendentes, tarefasRealizadas, idServico));
        }

        if (kanbans.isEmpty()) {
            throw new NoContentException("Não há dados disponíveis");
        }

        return kanbans;
    }

    public List<RegistroDto> retornarRetencaoUsuarios() {
        String sql = "SELECT * FROM vw_grafico";
        List<Object[]> registros = entityManager.createNativeQuery(sql).getResultList();

        List<RegistroDto> registrosDto = new ArrayList();
        for (Object[] registro : registros) {
            RegistroDto registroDto = new RegistroDto();

            registroDto.setAno((Integer) registro[0]);
            registroDto.setMes((String) registro[2]);
            registroDto.setEntraram((Long) registro[3]);
            registroDto.setSairam((Long) registro[4]);

            registrosDto.add(registroDto);
        }

        return registrosDto;
    }

    public RegistroKpiDto retornarRetencaoUsuariosKpis() {
        RegistroKpiDto registroDto = new RegistroKpiDto();

        String sql = "SELECT * FROM vw_ultimas_7_dias";
        Object ultimos7dias = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_ultimas_90_dias";
        Object ultimos90dias = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_retencao_usuario";
        Object retencao = entityManager.createNativeQuery(sql).getSingleResult();

        sql = "SELECT * FROM vw_curiosidades_usuario";
        List<Object[]> curiosidades = entityManager.createNativeQuery(sql).getResultList();

        registroDto.setUltimaSemana((Long) ultimos7dias);
        registroDto.setUltimosTresMeses((Long) ultimos90dias);
        registroDto.setTotal((Long) retencao);

        for (Object[] curiosidade : curiosidades) {
            Integer idTipoUsuario = (Integer) curiosidade[0];
            BigDecimal porcentagem = (BigDecimal) curiosidade[2];

            if (idTipoUsuario == 1) {
                registroDto.setPercentualContratantes(porcentagem);
            } else if (idTipoUsuario == 2) {
                registroDto.setPercentualProprietarios(porcentagem);
            }
        }

        return registroDto;
    }

    public ProprietarioKpiDto retornarProprietariosKpis() {
        ProprietarioKpiDto proprietarioDto = new ProprietarioKpiDto();

        String sql = "SELECT * FROM vw_buffet_15_sem_visitas";
        List<Object[]> buffetsInativos = entityManager.createNativeQuery(sql).getResultList();

        List<BuffetInativoDto> buffetsInativosDto = new ArrayList();
        for (Object[] buffetInativo : buffetsInativos) {
            BuffetInativoDto buffetInativoDto = new BuffetInativoDto();

            buffetInativoDto.setId((Integer) buffetInativo[0]);
            buffetInativoDto.setNome((String) buffetInativo[1]);
            buffetInativoDto.setUltimaVisita((Timestamp) buffetInativo[2]);

            buffetsInativosDto.add(buffetInativoDto);
        }

        proprietarioDto.setBuffetsInativos(buffetsInativosDto);

        sql = "SELECT * FROM vw_fracao_evento_acesso";
        List<Object> fracaoEventoAcesso = entityManager.createNativeQuery(sql).getResultList();

        List<ConversaoVisitasDto> conversaoVisitasDto = new ArrayList();
        for (Object fracao : fracaoEventoAcesso) {
            ConversaoVisitasDto conversaoVisitas = new ConversaoVisitasDto();

            conversaoVisitas.setId((Integer) ((Object[]) fracao)[0]);
            conversaoVisitas.setNome((String) ((Object[]) fracao)[1]);
            conversaoVisitas.setQuantidadeEventos((Long) ((Object[]) fracao)[2]);
            conversaoVisitas.setQuantidadeAcessos((Long) ((Object[]) fracao)[3]);

            conversaoVisitasDto.add(conversaoVisitas);
        }

        proprietarioDto.setBuffetsVisitasEventos(conversaoVisitasDto);

        sql = "SELECT COUNT(*) FROM evento WHERE status IN (2, 4, 7)";
        Object qtdEventosCancelados = entityManager.createNativeQuery(sql).getSingleResult();

        proprietarioDto.setQtdEventosCancelados((Long) qtdEventosCancelados);

        sql = "SELECT COUNT(*) FROM evento";
        Object qtdEventosTotal = entityManager.createNativeQuery(sql).getSingleResult();

        proprietarioDto.setQtdEventosTotal((Long) qtdEventosTotal);

        sql = "SELECT * FROM vw_categorias_qtd ORDER BY quantidade DESC";
        List<Object> categorias = entityManager.createNativeQuery(sql).getResultList();

        List<CategoriaKpiDto> categoriasDto = new ArrayList();
        for (Object categoria : categorias) {
            CategoriaKpiDto categoriaDto = new CategoriaKpiDto();

            categoriaDto.setCategoria((String) ((Object[]) categoria)[0]);
            categoriaDto.setQuantidade((Long) ((Object[]) categoria)[1]);

            categoriasDto.add(categoriaDto);
        }

        proprietarioDto.setCategorias(categoriasDto);

        return proprietarioDto;
    }

    public ContratanteKpi retornarContratanteKpi() {
        ContratanteKpi contratanteKpi = new ContratanteKpi();

        String sql = "SELECT * FROM vw_contratantes_consumo";
        List<Object> contratantes = entityManager.createNativeQuery(sql).getResultList();

        List<ContratanteKpi.ContratanteKpiData> contratantesDto = new ArrayList<>();
        for (Object contratante : contratantes) {
            ContratanteKpi contratanteKpiInstance = new ContratanteKpi();
            ContratanteKpi.ContratanteKpiData contratanteDto = contratanteKpiInstance.new ContratanteKpiData();

            contratanteDto.setIdUsuario((Integer) ((Object[]) contratante)[0]);
            contratanteDto.setNome((String) ((Object[]) contratante)[1]);

            Integer quantidade = (Long) ((Object[]) contratante)[2] != null ? ((Long) ((Object[]) contratante)[2]).intValue() : 0;
            contratanteDto.setQuantidadeEventos(quantidade);

            contratantesDto.add(contratanteDto);
        }

        long quantidadeContratantes = contratantesDto.stream().filter(contratante -> contratante.getQuantidadeEventos() == 0).count();
        contratanteKpi.setSemContratos((int) quantidadeContratantes);

        quantidadeContratantes = contratantesDto.stream().filter(contratante -> contratante.getQuantidadeEventos() == 1).count();
        contratanteKpi.setUmContrato((int) quantidadeContratantes);

        quantidadeContratantes = contratantesDto.stream().filter(contratante -> contratante.getQuantidadeEventos() >= 2).count();
        contratanteKpi.setDoisContratosOuMais((int) quantidadeContratantes);

        return contratanteKpi;
    }

    public FormularioDinamicoDto retornarFormularioDinamico() {
        FormularioDinamicoDto formularioDto = new FormularioDinamicoDto();

        String sql = "SELECT * FROM vw_utilizacao_formulario";
        Object formulario = entityManager.createNativeQuery(sql).getSingleResult();

        formularioDto.setUtilizacaoFormulario((BigDecimal) ((Object[]) formulario)[3]);

        sql = "SELECT * FROM vw_uso_formulario_dinamico";
        Object usoFormulario = entityManager.createNativeQuery(sql).getSingleResult();

        formularioDto.setPrecisaoFormulario((BigDecimal) ((Object[]) usoFormulario)[3]);

        sql = "SELECT * FROM vw_formulario_dinamico_consumo";
        List<Object> utilizacaoFormularioMensal = entityManager.createNativeQuery(sql).getResultList();

        List<UtlizacaoFormularioMensalDto> utilizacaoFormularioMensalDto = new ArrayList();
        for (Object utilizacao : utilizacaoFormularioMensal) {
            UtlizacaoFormularioMensalDto utilizacaoDto = new UtlizacaoFormularioMensalDto();

            utilizacaoDto.setMes((String) ((Object[]) utilizacao)[0]);
            utilizacaoDto.setQuantidadeUtilizacao((Long) ((Object[]) utilizacao)[1]);

            utilizacaoFormularioMensalDto.add(utilizacaoDto);
        }

        formularioDto.setUtilizacaoFormularioMensal(utilizacaoFormularioMensalDto);

        return formularioDto;
    }

    public BigDecimal retornarFaturamentoTotal() {
        String sql = "SELECT SUM(preco) FROM evento WHERE status IN (5, 6)";
        Object faturamento = entityManager.createNativeQuery(sql).getSingleResult();

        return (BigDecimal) faturamento;
    }

    public Double retornarAvaliacaoMedia() {
        String sql = "SELECT AVG(nota) FROM evento WHERE status IN (6)";
        Object avaliacao = entityManager.createNativeQuery(sql).getSingleResult();

        return (Double) avaliacao;
    }

    public BigDecimal retornarGastosTotal() {
        String sql = "SELECT SUM(valor) FROM transacao WHERE is_gasto = 1";
        Object gastos = entityManager.createNativeQuery(sql).getSingleResult();

        return (BigDecimal) gastos;
    }

    public TelaKpis retornarTelaKpis() {
        TelaKpis telaKpis = new TelaKpis();

        telaKpis.setFaturamentoTotal(retornarFaturamentoTotal());
        telaKpis.setGastosTotal(retornarGastosTotal());
        telaKpis.setAvaliacaoMedia(retornarAvaliacaoMedia());

        return telaKpis;
    }

    
}