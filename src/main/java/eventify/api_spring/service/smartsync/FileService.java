package eventify.api_spring.service.smartsync;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.File;
import eventify.api_spring.domain.smartsync.FileEvento;
import eventify.api_spring.repository.BuffetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    @Autowired
    private BuffetRepository buffetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<File> consultarTransacoes(Integer idBuffet) {
        List<File> ocorrencias = new ArrayList<>();
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

            ocorrencias.add(new File(pagante, cpf, email, valor.doubleValue(), is_gasto, motivo, data));
        }

        return ocorrencias;
    }

    @Transactional
    public List<FileEvento> consultarEventos(Integer idBuffet) {
        List<FileEvento> ocorrencias = new ArrayList<>();
        
        Query query = entityManager.createNativeQuery("SELECT id_evento, nome, cpf, email, data, preco, status, data_pedido FROM vw_info_eventos WHERE id_buffet = :idBuffet ;");
        query.setParameter("idBuffet", idBuffet);
        List<Object[]> eventos = query.getResultList();
        
        for (Object[] evento : eventos) {
            Integer idEvento = (Integer) evento[0];
            String nome = (String) evento[1];
            String cpf = (String) evento[2];
            String email = (String) evento[3];
            Timestamp data = (Timestamp) evento[4];
            BigDecimal precoBigDecimal = (BigDecimal) evento[5];
            Double preco = precoBigDecimal.doubleValue();
            String status = (String) evento[6];
            Timestamp dataPedido = (Timestamp) evento[7];

            ocorrencias.add(new FileEvento(idEvento, nome, cpf, email, data, preco, status, dataPedido));
        }

        return ocorrencias;
    }
    
    public String retornarTransacoesCsv(Integer idBuffet) {
        List<File> transacoes = consultarTransacoes(idBuffet);

        return gerarDadosTransacoesFormatoCsv(transacoes, idBuffet);
    }

    public String retornarEventosCsv(Integer idBuffet) {
        List<FileEvento> eventos = consultarEventos(idBuffet);

        return gerarDadosEventosFormatoCsv(eventos, idBuffet);
    }

    private String gerarDadosTransacoesFormatoCsv(List<File> transacoes, Integer idBuffet) {
        String arquivo = "pagante;cpf;email;valor;is_gasto;motivo;data\n";

        for (File linha : transacoes) {
            arquivo += String.format("%s;%s;%s;%.2f;%d;%s;%s\n", linha.getPagante(), linha.getCpf(), linha.getEmail(), linha.getValor(), linha.getIs_gasto(), linha.getMotivo(), linha.getData());
        }

        return arquivo;
    }

    public String gerarDadosEventosFormatoCsv(List<FileEvento> eventos, Integer idBuffet) {
        String arquivo = "id_evento;nome;cpf;email;data;preco;status;data_pedido\n";

        for (FileEvento linha : eventos) {
            arquivo += String.format("%d;%s;%s;%s;%s;%.2f;%s;%s\n", linha.getIdEvento(), linha.getNome(), linha.getCpf(), linha.getEmail(), linha.getData(), linha.getPreco(), linha.getStatus(), linha.getDataPedido());
        }

        return arquivo;
    }

    public String retornarNomeBuffet(Integer idBuffet) {
        String nome = buffetRepository.findById(idBuffet).get().getNome();

        return nome.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}