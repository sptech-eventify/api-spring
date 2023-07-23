package eventify.api_spring.domain.agenda;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import eventify.api_spring.domain.buffet.Buffet;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
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
}
