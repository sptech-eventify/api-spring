package eventify.api_spring.factory;

import eventify.api_spring.domain.TipoEvento;

import java.util.Set;

public class TipoEventoFactory {

    public static Set<TipoEvento> tiposEventos() {
        final TipoEvento casamento = new TipoEvento();
        casamento.setId(1);
        casamento.setDescricao("Casamento");

        final TipoEvento aniversario = new TipoEvento();
        aniversario.setId(2);
        aniversario.setDescricao("Aniversário");

        return Set.of(casamento, aniversario);
    }
}
