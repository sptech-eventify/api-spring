package eventify.api_spring.factory.pesquisa;

import java.util.List;

import eventify.api_spring.domain.buffet.Pesquisa;

public class PesquisaTestFactory {

    public static Pesquisa pesquisa() {

        return new Pesquisa(
                ""
                , null
                , null
                , null
                , List.of("Decorações", "Casamento")
                , null
                , null
                , null
                , null
                , null
                , null
        );
    }
}
