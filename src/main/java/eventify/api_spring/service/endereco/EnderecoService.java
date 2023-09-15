package eventify.api_spring.service.endereco;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;

import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.repository.EnderecoRepository;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> exibirTodosEnderecos() {
        return enderecoRepository.findAll();
    }

    public Endereco exibirEnderecoPorId(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);

        if (endereco.isEmpty()) {
            throw new NotFoundException("Endereço não encontrado na base de dados");
        }

        return endereco.get();
    }

    public Endereco cadastrarEndereco(Endereco endereco) {
        endereco.setValidado(false);
        System.out.println(endereco.toString());
        enderecoRepository.save(endereco);

        return endereco;
    }

    public Void atualizarEndereco(Integer id, Endereco endereco) {
        Optional<Endereco> enderecoAtualizado = enderecoRepository.findById(id);

        if (enderecoAtualizado.isEmpty()) {
            throw new NotFoundException("Endereço não encontrado na base de dados");
        }

        enderecoRepository.save(endereco);

        return null;
    }

    public void deletarEndereco(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);

        if (endereco.isEmpty()) {
            throw new NoContentException("Endereço não encontrado na base de dados");
        }
        Endereco enderecoAtualizado = endereco.get();
        enderecoAtualizado.setValidado(false);
        enderecoRepository.save(enderecoAtualizado);
    }

}
