package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Pesquisa;
import eventify.api_spring.domain.buffet.TipoEvento;
import eventify.api_spring.dto.buffet.BuffetConsultaDto;
import eventify.api_spring.dto.buffet.BuffetRespostaDto;
import eventify.api_spring.dto.imagem.ImagemDto;
import eventify.api_spring.dto.utils.objects.HashTable;
import eventify.api_spring.mapper.buffet.BuffetMapper;
import eventify.api_spring.repository.BuffetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eventify.api_spring.exception.http.NoContentException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PesquisaService {
    private final BuffetRepository buffetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BuffetMapper buffetMapper;

    @Autowired
    private TipoEventoService tipoEventoService;

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

    private Pageable createPageRequestUsing(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    public Page<BuffetRespostaDto> getBuffetPorPesquisa(Pesquisa pesquisa, Integer page, Integer size) {
        List<BuffetRespostaDto> buffetsFiltrados = buffetRepository.findAllBuffet().stream()
                .filter(buffet -> buffet.getNome() != null &&
                        buffet.getNome().toLowerCase().contains(pesquisa.getNome().toLowerCase()))
                .filter(buffet -> pesquisa.getTipoEvento() == null || pesquisa.getTipoEvento().isEmpty() ||
                        buffet.getTiposEventos().isEmpty() ||
                        buffet.getTiposEventos().stream().anyMatch(tipoEvento ->
                                pesquisa.getTipoEvento().stream().anyMatch(t -> t.equalsIgnoreCase(tipoEvento.getDescricao()))))
                .filter(buffet -> pesquisa.getFaixaEtaria() == null || pesquisa.getFaixaEtaria().isEmpty() ||
                        buffet.getFaixaEtarias().isEmpty() ||
                        buffet.getFaixaEtarias().stream().anyMatch(faixaEtaria ->
                                pesquisa.getFaixaEtaria().stream().anyMatch(f -> f.equalsIgnoreCase(faixaEtaria.getDescricao()))))
                .filter(buffet -> pesquisa.getServico() == null || pesquisa.getServico().isEmpty() ||
                        buffet.getServicos().isEmpty() ||
                        buffet.getServicos().stream().anyMatch(servico ->
                                pesquisa.getServico().stream().anyMatch(s -> s.equalsIgnoreCase(servico.getDescricao()))))
                .filter(buffet -> pesquisa.getQtdPessoas() == null || pesquisa.getQtdPessoas() <= buffet.getQtdPessoas())
                .filter(buffet -> pesquisa.getOrcMin() == null || pesquisa.getOrcMin() <= buffet.getPrecoMedioDiaria())
                .filter(buffet -> pesquisa.getOrcMax() == null || pesquisa.getOrcMax() >= buffet.getPrecoMedioDiaria())
                .filter(buffet -> pesquisa.getTamanho() == null || pesquisa.getTamanho() <= buffet.getTamanho())
                .filter(buffet -> pesquisa.getLatitude() == null || pesquisa.getLongitude() == null ||
                        calcularDistancia(pesquisa.getLatitude(), pesquisa.getLongitude(),
                                buffet.getEndereco().getLatitude(), buffet.getEndereco().getLongitude()) <= 15)
                .map(buffetMapper::toRespostaDto)
                .collect(Collectors.toList());

        Pageable pageRequest = createPageRequestUsing(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), buffetsFiltrados.size());

        List<BuffetRespostaDto> pageContent = buffetsFiltrados.subList(start, end);

        if (buffetsFiltrados.isEmpty()) {
            throw new NoContentException("Não há buffets que atendam aos critérios da pesquisa");
        }

        return new PageImpl<>(pageContent, pageRequest, buffetsFiltrados.size());
    }

    public Page<BuffetRespostaDto> getTodosBuffets(Integer page, Integer size) {
        List<BuffetRespostaDto> buffets = buffetRepository.findAllBuffet().stream()
                .map(buffetMapper::toRespostaDto)
                .collect(Collectors.toList());

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        Pageable pageRequest = createPageRequestUsing(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), buffets.size());

        List<BuffetRespostaDto> pageContent = buffets.subList(start, end);

        if (buffets.isEmpty()) {
            throw new NoContentException("Não há buffets que atendam aos critérios da pesquisa");
        }

        return new PageImpl<>(pageContent, pageRequest, buffets.size());
    }

    public List<ImagemDto> converterImagens(String imagens) {
        List<ImagemDto> lista = new ArrayList<>();

        String[] imagensArray = imagens.split(",");

        for (String imagem : imagensArray) {
            ImagemDto imagemDto = new ImagemDto();
            imagemDto.setCaminho(imagem);
            lista.add(imagemDto);
        }

        return lista;
    }

    public List<TipoEvento> converterEventos(String tiposEventos) {
        List<TipoEvento> lista = new ArrayList<>();
        List<TipoEvento> eventosExistentes = tipoEventoService.tiposEventos();

        String[] tipos = tiposEventos.split(",");

        for (String tipo : tipos) {
            for (TipoEvento evento : eventosExistentes) {
                if (evento.getDescricao().equalsIgnoreCase(tipo)) {
                    lista.add(evento);
                }
            }
        }

        return lista;
    }

    public List<BuffetConsultaDto> getBuffetConsultaDto() {
        List<BuffetConsultaDto> lista = new ArrayList<>();
    
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_buffet_info");
        List<Object[]> buffets = query.getResultList();
    
        for (Object[] buffet : buffets) {    
            BuffetConsultaDto buffetResumoDto = new BuffetConsultaDto();
            buffetResumoDto.setId((Integer) buffet[0]);
            buffetResumoDto.setTiposEventos(this.converterEventos((String) buffet[1]));
            buffetResumoDto.setNome((String) buffet[2]);
            buffetResumoDto.setPrecoMedioDiaria(((BigDecimal) buffet[3]).doubleValue());
    
            Double nota = (Double) buffet[4];

            if (Objects.isNull(buffet[4])) {
                buffetResumoDto.setNotaMediaAvaliacao(0.0);
            } else {
                buffetResumoDto.setNotaMediaAvaliacao(nota);
            }
    
            buffetResumoDto.setImagens(converterImagens((String) buffet[5]));
    
            lista.add(buffetResumoDto);
        }
    
        return lista;
    }

    public List<Object> getNotas(){
        Query query = entityManager.createNativeQuery("SELECT * FROM vw_notas_buffet");
        List<Object> notas = query.getResultList();

        if (notas.isEmpty()){
            throw new NoContentException("Não há notas cadastradas");
        }

        return notas;
    }

    public List<BuffetConsultaDto> getBuffetsPorNota(Integer nota) {
        HashTable hashTable = new HashTable(6);
        List<BuffetConsultaDto> lista = this.getBuffetConsultaDto();

        if (lista.isEmpty()) {
            throw new NoContentException("Não há buffets cadastrados");
        }

        for (BuffetConsultaDto buffet : lista) {
            hashTable.insere(buffet);
        }

        return hashTable.busca(nota);
    }

    public static double calcularDistancia(double lat1, double long1, double lat2, double long2) {
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
