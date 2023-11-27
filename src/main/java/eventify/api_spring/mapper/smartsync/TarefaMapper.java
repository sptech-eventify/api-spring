package eventify.api_spring.mapper.smartsync;

import eventify.api_spring.domain.smartsync.Tarefa;
import eventify.api_spring.dto.smartsync.TarefaDto;

public class TarefaMapper {
    public static TarefaDto toDto(Tarefa domain) {
        TarefaDto dto = new TarefaDto();

        dto.setId(domain.getId());
        dto.setNome(domain.getNome());
        dto.setDescricao(domain.getDescricao());
        dto.setFibonacci(domain.getFibonacci());
        dto.setStatus(domain.getStatus());
        dto.setHorasEstimada(domain.getHorasEstimada());
        dto.setDataEstimada(domain.getDataEstimada());
        dto.setDataCriacao(domain.getDataCriacao());
        dto.setDataConclusao(domain.getDataConclusao());
        dto.setIsVisivel(domain.getIsVisivel());
        dto.setIdServico(domain.getBucket().getBuffetServico().getServico().getId());

        if (domain.getTarefaPai() != null) {
            dto.setIdTarefaPai(domain.getTarefaPai().getId());
        }

        dto.setIdBucket(domain.getBucket().getId());

        return dto;
    }
}
