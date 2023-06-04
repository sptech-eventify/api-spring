package eventify.api_spring.dto;

import java.util.List;

public record BuffetPublicDto(String nome, String descricao, Object imagens, Double precoMedioDiaria, Double notaMediaAvaliacao, Object servicos, Object faixasEtarias, Object tiposEventos) {
}
