package eventify.api_spring.dto.agenda;

import java.time.LocalDateTime;

public class AgendaDto {
    private Integer id;
    private LocalDateTime data;

    public AgendaDto(Integer id, LocalDateTime data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
