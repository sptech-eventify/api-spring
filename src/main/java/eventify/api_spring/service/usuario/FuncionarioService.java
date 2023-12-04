package eventify.api_spring.service.usuario;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.NivelAcesso;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.FuncionarioCadastrarDto;
import eventify.api_spring.dto.usuario.FuncionarioDevolverDto;
import eventify.api_spring.exception.http.ConflictException;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.exception.http.NotFoundException;
import eventify.api_spring.mapper.usuario.FuncionarioMapper;
import eventify.api_spring.repository.FuncionarioRepository;
import eventify.api_spring.repository.NivelAcessoRepository;
import eventify.api_spring.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<FuncionarioDevolverDto> exibirTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()){
            throw new NoContentException("Não há funcionários cadastrados");
        }

        List<FuncionarioDevolverDto> funcionariosDevolverDto = new ArrayList();
        for (Funcionario funcionario : funcionarios) {
            funcionariosDevolverDto.add(FuncionarioMapper.toDevolverDto(funcionario));
        }
        

        return funcionariosDevolverDto;
    }

    public FuncionarioDevolverDto exibirFuncionarioPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
        FuncionarioDevolverDto funcionarioDevolverDto = FuncionarioMapper.toDevolverDto(funcionario);

        return funcionarioDevolverDto;
    }

    public Funcionario criarFuncionario(FuncionarioCadastrarDto funcionario) {
        Optional<Usuario> empregador = usuarioRepository.findById(funcionario.getIdEmpregador());

        if (empregador.isEmpty()) {
            throw new NotFoundException("Empregador não encontrado");
        }

        Optional<Usuario> usuario = usuarioRepository.findByEmail(funcionario.getEmail());
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(funcionario.getEmail());

        if (usuario.isPresent()) {
            throw new ConflictException("Email já cadastrado");
        }

        if (funcionarioOpt.isPresent()) {
            throw new ConflictException("Funcionário já cadastrado");
        }

        if (usuario.get().getCpf().equals(funcionario.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (funcionarioOpt.get().getCpf().equals(funcionario.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        Optional<NivelAcesso> nivelAcesso = nivelAcessoRepository.findById(funcionario.getIdNivelAcesso());

        if (nivelAcesso.isEmpty()) {
            throw new NotFoundException("Nível de acesso não encontrado");
        }

        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));

        Funcionario funcionarioCriado = FuncionarioMapper.toDomain(funcionario, empregador.get(), nivelAcesso.get());

        return funcionarioRepository.save(funcionarioCriado);
    }

    public FuncionarioDevolverDto atualizarFuncionario(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
        
        Optional<Usuario> empregador = usuarioRepository.findById(funcionario.getEmpregador().getId());

        if (empregador.isEmpty()) {
            throw new NotFoundException("Empregador não encontrado");
        }

        Optional<Usuario> usuario = usuarioRepository.findByEmail(funcionario.getEmail());
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(funcionario.getEmail());

        if (usuario.isPresent()) {
            throw new ConflictException("Email já cadastrado");
        }

        if (funcionarioOpt.isPresent()) {
            throw new ConflictException("Funcionário já cadastrado");
        }

        if (usuario.get().getCpf().equals(funcionario.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (funcionarioOpt.get().getCpf().equals(funcionario.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        Optional<NivelAcesso> nivelAcesso = nivelAcessoRepository.findById(funcionario.getNivelAcesso().getId());

        if (nivelAcesso.isEmpty()) {
            throw new NotFoundException("Nível de acesso não encontrado");
        }

        Funcionario atualizado = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDevolverDto(atualizado);
    }

    public void deletarFuncionario(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));

        funcionario.setIsVisivel(false);

        funcionarioRepository.save(funcionario);
    }
}

