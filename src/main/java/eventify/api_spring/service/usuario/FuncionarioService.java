package eventify.api_spring.service.usuario;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.NivelAcesso;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.FuncionarioCadastrarDto;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.usuario.FuncionarioMapper;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.NivelAcessoRepository;
import eventify.api_spring.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NivelAcessoRepository nivelAcessoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Funcionario> exibirTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()){
            throw new NoContentException("Não há funcionários cadastrados");
        }

        return funcionarios;
    }

    public Funcionario exibirFuncionarioPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        return funcionario;
    }

    public Funcionario criarFuncionario(FuncionarioCadastrarDto funcionario) {
        Optional<Usuario> empregador = usuarioRepository.findById(funcionario.getIdEmpregador());

        if (empregador.isEmpty()) {
            throw new NotFoundException("Empregador não encontrado");
        }

        Optional<NivelAcesso> nivelAcesso = nivelAcessoRepository.findById(funcionario.getIdNivelAcesso());

        if (nivelAcesso.isEmpty()) {
            throw new NotFoundException("Nível de acesso não encontrado");
        }

        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));

        Funcionario funcionarioCriado = FuncionarioMapper.toDomain(funcionario, empregador.get(), nivelAcesso.get());

        return funcionarioRepository.save(funcionarioCriado);
    }

    public Funcionario atualizarFuncionario(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        return funcionarioRepository.save(funcionario);
    }

    public void deletarFuncionario(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        funcionario.setIsVisivel(false);

        funcionarioRepository.save(funcionario);
    }
}

