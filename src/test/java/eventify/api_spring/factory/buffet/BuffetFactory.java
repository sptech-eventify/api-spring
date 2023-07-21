package eventify.api_spring.factory.buffet;

import eventify.api_spring.factory.tipoevento.TipoEventoFactory;
import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.factory.agenda.AgendaFactory;
import eventify.api_spring.factory.usuario.UsuarioFactory;

public class BuffetFactory {

    public static Buffet buffet() {
        final Buffet buffet = new Buffet();
        buffet.setId(1);
        buffet.setNome("Giussepe Cadura");
        buffet.setAgendas(AgendaFactory.agenda(buffet));
        buffet.setUsuario(UsuarioFactory.usuario());
        buffet.setTiposEventos(TipoEventoFactory.tiposEventos());

        return buffet;
    }

}
