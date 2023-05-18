package eventify.api_spring.service;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Evento;
import eventify.api_spring.domain.TipoEvento;
import eventify.api_spring.dto.DataDto;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.repository.EventoRepository;
import eventify.api_spring.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuffetService {

    @Autowired
    private BuffetRepository buffetRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ImagemRepository imagemRepository;

    public List<Buffet> listar() {
        return buffetRepository.findAll();
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
            return eventoRepository.findByBuffet(buffet).stream()
                    .mapToDouble(Evento::getNota)
                    .average()
                    .orElse(0.0);
        }
        return null;
    }

    public String pegarCaminhoImagem(int idBuffet) {
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

    public void cadastrar (Buffet buffet) {
        buffetRepository.save(buffet);
    }

    public void atualizar (Buffet buffet) {
        buffetRepository.save(buffet);
    }

}
