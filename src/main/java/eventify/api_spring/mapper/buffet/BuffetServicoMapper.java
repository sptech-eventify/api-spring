package eventify.api_spring.mapper.buffet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import eventify.api_spring.domain.buffet.Buffet;
import eventify.api_spring.domain.buffet.BuffetServico;
import eventify.api_spring.domain.buffet.Servico;


public class BuffetServicoMapper {
    public static List<Servico> toListServico(Set<BuffetServico> listaBuffetServico) {
        return listaBuffetServico.stream().map(BuffetServico::getServico).toList();
    }

    public static Set<Servico> toSetServico(Set<BuffetServico> listaBuffetServico) {
        return listaBuffetServico.stream().map(BuffetServico::getServico).collect(Collectors.toSet());
    }

    public static List<Buffet> toListBuffet(Set<BuffetServico> listaBuffetServico) {
        return listaBuffetServico.stream().map(BuffetServico::getBuffet).toList();
    }

    public static Set<Buffet> toSetBuffet(Set<BuffetServico> listaBuffetServico) {
        return listaBuffetServico.stream().map(BuffetServico::getBuffet).collect(Collectors.toSet());
    }
}