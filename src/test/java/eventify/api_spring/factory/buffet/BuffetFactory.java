package eventify.api_spring.factory.buffet;

import eventify.api_spring.domain.Agenda;
import eventify.api_spring.domain.Buffet;
import eventify.api_spring.factory.usuario.UsuarioFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BuffetFactory {

    public static Buffet buffet(){
        final Buffet buffet = new Buffet();
        buffet.setId(1);
        buffet.setNome("Giussepe Cadura");
        buffet.setAgendas(agenda(buffet));
        buffet.setUsuario(UsuarioFactory.usuario());

        return buffet;
    }

    private static List<Agenda> agenda(Buffet buffet){

        Agenda agenda = new Agenda();

        LocalDateTime data = LocalDateTime.of(2022,11,25,16,44,2);

        agenda.setId(1);
        agenda.setData(data);
        agenda.setBuffet(buffet);

        return List.of(agenda);
    }
}
