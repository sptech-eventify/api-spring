package eventify.api_spring.domain.agenda;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import eventify.api_spring.domain.buffet.Buffet;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "id_buffet")
    private Buffet buffet;

    private Boolean isAtivo;

}