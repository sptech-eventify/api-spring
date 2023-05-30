package eventify.api_spring.factory.pesquisa;

import eventify.api_spring.domain.Pesquisa;

import java.util.List;

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
