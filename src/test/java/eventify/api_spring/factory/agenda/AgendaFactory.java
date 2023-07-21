package eventify.api_spring.factory.agenda;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.buffet.Buffet;

import java.time.LocalDateTime;
import java.util.List;

public class AgendaFactory {

    public static List<Agenda> agenda(Buffet buffet){

        final Agenda agenda1 = new Agenda();
        final Agenda agenda2 = new Agenda();

        final LocalDateTime data1 = LocalDateTime.of(2022,11,25,16,44,2);
        final LocalDateTime data2 = LocalDateTime.of(2023, 8,13,23,48,34);

        agenda1.setId(1);
        agenda1.setData(data1);
        agenda1.setBuffet(buffet);

        agenda2.setId(2);
        agenda2.setData(data2);
        agenda2.setBuffet(buffet);

        return List.of(agenda1, agenda2);
    }
}
