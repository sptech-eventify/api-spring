package eventify.api_spring.service.smartsync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.domain.smartsync.File;
import eventify.api_spring.repository.BuffetRepository;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    @Autowired
    private BuffetRepository buffetRepository;

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
    
    public String retornarCsv(Integer idBuffet) {
        List<File> transacoes = consultarTransacoes(idBuffet);

        return gerarDadosFormatoCsv(transacoes, idBuffet);
    }

    private String gerarDadosFormatoCsv(List<File> transacoes, Integer idBuffet) {
        String arquivo = "pagante;cpf;email;valor;is_gasto;motivo;data\n";

        for (File linha : transacoes) {
            arquivo += String.format("%s;%s;%s;%.2f;%d;%s;%s\n", linha.getPagante(), linha.getCpf(), linha.getEmail(), linha.getValor(), linha.getIs_gasto(), linha.getMotivo(), linha.getData());
        }

        return arquivo;
    }

    public String retornarNomeBuffet(Integer idBuffet) {
        String nome = buffetRepository.findById(idBuffet).get().getNome();

        return nome.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}