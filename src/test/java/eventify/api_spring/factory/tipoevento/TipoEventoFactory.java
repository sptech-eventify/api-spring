package eventify.api_spring.factory.tipoevento;

import java.util.Set;

import eventify.api_spring.domain.buffet.TipoEvento;

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
