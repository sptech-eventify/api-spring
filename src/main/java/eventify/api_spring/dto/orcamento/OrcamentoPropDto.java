package eventify.api_spring.dto.orcamento;

import java.time.LocalDate;

public record OrcamentoPropDto(Integer id, String nomeContratante, LocalDate data, Double preco, String status) {
}
