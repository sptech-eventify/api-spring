package eventify.api_spring.factory.agenda;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.domain.Buffet;

import java.time.LocalDateTime;
import java.util.List;

public class AgendaFactory {

    public static List<Agenda> agenda(Buffet buffet){

        Agenda agenda = new Agenda();

        LocalDateTime data = LocalDateTime.of(2022,11,25,16,44,2);

        agenda.setId(1);
        agenda.setData(data);
        agenda.setBuffet(buffet);

        return List.of(agenda);
    }
}
