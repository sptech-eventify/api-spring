package eventify.api_spring.service.smartsync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.repository.BuffetRepository;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    private final static String PATH = "src/main/java/com/eventify/data/";
    private final static String FILE_NAME = "transacoes";

    @Autowired
    private BuffetRepository buffetRepository;

    @Transactional
    public List<eventify.api_spring.domain.smartsync.File> consultarTransacoes(Integer idBuffet) {
        List<eventify.api_spring.domain.smartsync.File> ocorrencias = new ArrayList<>();
        List<Object[]> transacoes = buffetRepository.spTransacoes(idBuffet);

        for (Object[] transacao : transacoes) {
            BigDecimal valor = (BigDecimal) transacao[0];
            Long isGastoLong = (Long) transacao[1];
            Integer is_gasto = isGastoLong.intValue(); // Converta Long para Integer
            String motivo = (String) transacao[2];
            String data = (String) transacao[3];
        
            ocorrencias.add(new eventify.api_spring.domain.smartsync.File(valor.doubleValue(), is_gasto, motivo, data));
        }

        return ocorrencias;
    }
    
    public File retornarCsv(Integer idBuffet) throws IOException {
        List<eventify.api_spring.domain.smartsync.File> transacoes = consultarTransacoes(idBuffet);

        return createCSVFile(transacoes, idBuffet);
    }

    private File createCSVFile(List<eventify.api_spring.domain.smartsync.File> transacoes, Integer idBuffet) throws IOException {
        File csvFile = new File(PATH + FILE_NAME + idBuffet + ".csv");

        try (Writer writer = new FileWriter(csvFile)) {
            writer.write("valor;is_gasto;motivo;data\n");

            for (eventify.api_spring.domain.smartsync.File linha : transacoes) {
                writer.write(linha.getValor() + ";" + linha.getIs_gasto() + ";" + linha.getMotivo() + ";" + linha.getData() + "\n");
            }
        }
        
        return csvFile;
    }
}
