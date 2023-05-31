package eventify.api_spring.service;

import eventify.api_spring.domain.Pesquisa;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.mapper.BuffetMapper;
import eventify.api_spring.repository.BuffetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesquisaService {
    private final BuffetRepository buffetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PesquisaService(BuffetRepository buffetRepository) {
        this.buffetRepository = buffetRepository;
    }

    public Boolean containsStringIgnoreCase(String[] array, String targetString) {
        for (String str : array) {
            if (str.equalsIgnoreCase(targetString)) {
                return true;
            }
        }
        return false;
    }

    public List<BuffetDtoResposta> getBuffetPorPesquisa(Pesquisa pesquisa) {
        System.out.println("Pesquisa: " + pesquisa.toString());

        List<BuffetDtoResposta> buffetsFiltrados = buffetRepository.findAllBuffet().stream()
                .filter(buffet -> pesquisa.getNome().isEmpty() || buffet.getNome().toLowerCase().contains(pesquisa.getNome().toLowerCase()))

                .filter(buffet -> pesquisa.getTipoEvento() == null || buffet.getTiposEventos().stream()
                        .anyMatch(tipoEvento -> pesquisa.getTipoEvento().stream().anyMatch(t -> t.equalsIgnoreCase(tipoEvento.getDescricao()))))

                .filter(buffet -> pesquisa.getFaixaEtaria() == null || buffet.getFaixaEtarias().stream()
                        .anyMatch(faixaEtaria -> pesquisa.getFaixaEtaria().stream().anyMatch(f -> f.equalsIgnoreCase(faixaEtaria.getDescricao()))))

                .filter(buffet -> pesquisa.getServico() == null || buffet.getServicos().stream()
                        .anyMatch(servico -> pesquisa.getServico().stream().anyMatch(s -> s.equalsIgnoreCase(servico.getDescricao()))))

                .filter(buffet -> pesquisa.getQtdPessoas() == null || pesquisa.getQtdPessoas() <= buffet.getQtdPessoas())
                .filter(buffet -> pesquisa.getOrcMin() == null || pesquisa.getOrcMin() <= buffet.getPrecoMedioDiaria())
                .filter(buffet -> pesquisa.getOrcMax() == null || pesquisa.getOrcMax() >= buffet.getPrecoMedioDiaria())
                .filter(buffet -> pesquisa.getTamanho() == null || pesquisa.getTamanho() <= buffet.getTamanho())
                .filter(buffet -> pesquisa.getLatitude() == null || pesquisa.getLongitude() == null
                        || calcularDistancia(pesquisa.getLatitude(), pesquisa.getLongitude(),
                        buffet.getEndereco().getLatitude(), buffet.getEndereco().getLongitude()) <= 15)
                .map(BuffetMapper::toDto)
                .collect(Collectors.toList());

        return buffetsFiltrados;
    }

    public List<BuffetDtoResposta> getTodosBuffets() {
        return buffetRepository.findAllBuffet().stream()
                .map(BuffetMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Object> getNotas(){
        String sql = "SELECT * FROM vw_notas_buffet";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> resultados = query.getResultList();
        return resultados;
    }

    public static double calcularDistancia(double lat1, double long1, double lat2, double long2) {
        // raio médio da Terra em quilômetros
        double raioTerra = 6371;

        double difLat = Math.toRadians(lat2 - lat1);
        double difLong = Math.toRadians(long2 - long1);

        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(difLong / 2) * Math.sin(difLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia = raioTerra * c;

        return distancia;
    }
}
