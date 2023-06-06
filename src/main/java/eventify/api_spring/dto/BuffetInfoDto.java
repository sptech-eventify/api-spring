package eventify.api_spring.dto;



public record BuffetInfoDto(Integer id, Object tiposEventos, String nome, Double precoMedioDiaria, Integer tamanho, Integer qtdPessoas, Double notaMedia, Object caminho) {
}

