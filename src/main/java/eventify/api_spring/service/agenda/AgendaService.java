package eventify.api_spring.service.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.dto.agenda.AgendaCriacaoDto;
import eventify.api_spring.dto.agenda.AgendaDto;
import eventify.api_spring.mapper.agenda.AgendaMapper;
import eventify.api_spring.repository.AgendaRepository;
import eventify.api_spring.repository.BuffetRepository;
import eventify.api_spring.service.buffet.BuffetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.exception.http.ConflictException;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private BuffetService buffetService;

    @Autowired
    private BuffetRepository buffetRepository;

    public List<AgendaDto> listarAgendas() {
        List<Agenda> agendas = agendaRepository.findAll();

        if (agendas.isEmpty()) {
            throw new NoContentException("Não há eventos cadastrados na base de dados");
        }

        return agendas.stream().map(AgendaMapper::toDto).collect(Collectors.toList());
    }

    public Agenda criarAgenda(AgendaCriacaoDto agendaCriacao) {
        if (Objects.isNull(buffetService.buscarBuffetPorId(agendaCriacao.getIdBuffet()))) {
            throw new NotFoundException("Usuário não encontrado na base de dados");
        }

        LocalDate dataEvento = agendaCriacao.getData().toLocalDate();

        if (agendaRepository.findByBuffetIdAndDataEquals(agendaCriacao.getIdBuffet(), dataEvento).isPresent()) {
            throw new ConflictException("A data solicitada já está ocupada");
        }

        Agenda agenda = AgendaMapper.toDomain(agendaCriacao);
        agendaRepository.save(agenda);

        return agenda;
    }

    public AgendaDto buscarAgendaPorId(Integer idAgenda) {
        Optional<Agenda> agenda = agendaRepository.findById(idAgenda);

        if (agenda.isEmpty()) {
            throw new NotFoundException("Evento não encontrado na base de dados");
        }

        return AgendaMapper.toDto(agenda.get());
    }

    public List<AgendaDto> buscarAgendaPorBuffet(Integer idBuffet) {
        Optional<Buffet> buffet = buffetRepository.findById(idBuffet);

        if (buffet.isEmpty()) {
            throw new NotFoundException("Buffet não encontrado na base de dados");
        }

        List<Agenda> agendas = agendaRepository.findByBuffetId(idBuffet);

        if (agendas.isEmpty()) {
            throw new NoContentException("Não há eventos cadastrados para este buffet");
        }

        return agendas.stream().map(AgendaMapper::toDto).collect(Collectors.toList());
    }

    public void deletarAgenda(Integer idAgenda) {
        Optional<Agenda> agenda = agendaRepository.findById(idAgenda);

        if (agenda.isEmpty()) {
            throw new NotFoundException("Evento não encontrado na base de dados");
        }
        Agenda agendaAtualizada = agenda.get();
        agendaAtualizada.setIsAtivo(false);
        agendaRepository.save(agendaAtualizada);
    }
}