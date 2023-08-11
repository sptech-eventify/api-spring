package eventify.api_spring.factory.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.buffet.Buffet;

import java.time.LocalDateTime;
import java.util.List;

public class AgendaFactory {

    public static List<Agenda> agenda(Buffet buffet){

        final LocalDateTime data1 = LocalDateTime.of(2022,11,25,16,44,2);
        final LocalDateTime data2 = LocalDateTime.of(2023, 8,13,23,48,34);

        final Agenda agenda1 = new Agenda(1, data1, buffet);
        final Agenda agenda2 = new Agenda(2, data2, buffet);

        return List.of(agenda1, agenda2);
    }
}
