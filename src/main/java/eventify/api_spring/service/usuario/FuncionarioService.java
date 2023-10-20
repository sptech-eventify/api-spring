package eventify.api_spring.service.usuario;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

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

    public Funcionario criarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
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

