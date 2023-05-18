package eventify.api_spring.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @FutureOrPresent
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;

    public Agenda(Integer id, LocalDateTime data, Buffet buffet) {
        this.id = id;
        this.data = data;
        this.buffet = buffet;
    }

    public Agenda() {
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

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }
}
