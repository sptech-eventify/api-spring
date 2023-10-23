package eventify.api_spring.mapper.usuario;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.NivelAcesso;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.FuncionarioCadastrarDto;
import eventify.api_spring.dto.usuario.FuncionarioDevolverDto;
import eventify.api_spring.dto.usuario.SmartSyncUsuarioTokenDto;

public class FuncionarioMapper {
    public static SmartSyncUsuarioTokenDto ofFuncionario(Funcionario funcionario, String token) {
        SmartSyncUsuarioTokenDto usuarioTokenDto = new SmartSyncUsuarioTokenDto(funcionario.getId(),
            funcionario.getNome(),
            funcionario.getEmail(),
            funcionario.getCpf(),
            1,
            funcionario.getNivelAcesso(),
            token);
  
        return usuarioTokenDto;
    }

    public static Funcionario toDomain(FuncionarioCadastrarDto dto, Usuario empregador, NivelAcesso nivelAcesso) {
        Funcionario domain = new Funcionario();
        domain.setNome(dto.getNome());
        domain.setCpf(dto.getCpf());
        domain.setEmail(dto.getEmail());
        domain.setSenha(dto.getSenha());
        domain.setTelefone(dto.getTelefone());
        domain.setSalario(dto.getSalario());
        domain.setDiaPagamento(dto.getDiaPagamento());
        domain.setUsuario(empregador);
        domain.setNivelAcesso(nivelAcesso);

        return domain;
    }

    public static FuncionarioDevolverDto toDevolverDto(Funcionario domain) {
        FuncionarioDevolverDto dto = new FuncionarioDevolverDto();

        dto.setId(domain.getId());
        dto.setNome(domain.getNome());
        dto.setCpf(domain.getCpf());
        dto.setEmail(domain.getEmail());
        dto.setTelefone(domain.getTelefone());
        dto.setNivelAcesso(domain.getNivelAcesso());
        dto.setSalario(domain.getSalario());
        
        return dto;
    }
}
