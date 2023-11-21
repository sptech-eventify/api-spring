package eventify.api_spring.mapper.endereco;

import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.dto.endereco.EnderecoRespostaDto;

public class EnderecoMapper {
    public static EnderecoRespostaDto toDto(Endereco domain) {
        EnderecoRespostaDto dto = new EnderecoRespostaDto();

        dto.setId(domain.getId());
        dto.setLogradouro(domain.getLogradouro());
        dto.setNumero(domain.getNumero());
        dto.setBairro(domain.getBairro());
        dto.setCidade(domain.getCidade());
        dto.setUf(domain.getUf());
        dto.setCep(domain.getCep());
        dto.setLatitude(domain.getLatitude());
        dto.setLongitude(domain.getLongitude());

        return dto;
    }
}