package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.dto.AgendaDto;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.dto.BuffetInfoDto;
import eventify.api_spring.dto.DataDto;
import eventify.api_spring.mapper.BuffetMapper;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.ImagemRepository;
import eventify.api_spring.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class BuffetService {
    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private EventoRepository eventoRepository;
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

    public List<Buffet> getBufferPorPesquisaNome(String q){
        return buffetRepository.findByNomeContainingIgnoreCase(q);
    }

    public List<String> getTipoEventos() {
        List<Buffet> buffets = buffetRepository.findAll();
        List<String> tipos  = new ArrayList<>();
        for (Buffet buffet : buffets) {
            for (TipoEvento evento  : buffet.getTiposEventos()) {
                if (!tipos.contains(evento.getDescricao())) {
                    tipos.add(evento.getDescricao())    ;
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
                datasDto.add(new DataDto(data.getYear(),data.getMonthValue(),data.getDayOfMonth()));
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

    /*public List<BuffetInfoDto> pegarBuffetInfo() {
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_buffet_info");
        List<Object[]> result = query.getResultList();

        List<BuffetInfoDto> buffetInfoList = new ArrayList<>();

        for (Object[] row : result) {
            List<String> descricoes = Arrays.asList(((String) row[0]).split(","));
            String nome = (String) row[1];
            Double precoMediaDiaria = ((BigDecimal) row[2]).doubleValue();
            Double notaMediaAvaliacao = (Double) row[3];
            List<String> caminhos = Arrays.asList(((String) row[4]).split(","));

            BuffetInfoDto buffetInfo = new BuffetInfoDto(descricoes, nome, precoMediaDiaria, notaMediaAvaliacao, caminhos);
            buffetInfoList.add(buffetInfo);
        }


        return buffetInfoList;
    }*/

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


    public void cadastrar (Buffet buffet) {buffetRepository.save(buffet);
    }

    public void atualizar (Buffet buffet) {
        buffetRepository.save(buffet);
    }

}
