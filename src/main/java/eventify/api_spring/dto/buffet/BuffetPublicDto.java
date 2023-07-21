package eventify.api_spring.dto.buffet;

public record BuffetPublicDto(
        String nome,

        String proprietario,
        String descricao,
        Object imagens,
        Double precoMedioDiaria,
        Double notaMediaAvaliacao,
        Object servicos,
        Object faixasEtarias,
        Object tiposEventos,
        Double latitude,
        Double longitude,
        String logradouro,
        Integer numero,
        String bairro,
        String cidade,
        String uf,
        String cep) {
}
