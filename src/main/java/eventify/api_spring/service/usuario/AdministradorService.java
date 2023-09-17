package eventify.api_spring.service.usuario;

import eventify.api_spring.dto.endereco.EnderecoDto;
import eventify.api_spring.dto.usuario.UsuarioAdminDto;
import eventify.api_spring.dto.usuario.UsuarioBanidoDto;
import eventify.api_spring.dto.utils.ChurnDto;
import eventify.api_spring.dto.utils.ConversaoReservasDto;
import eventify.api_spring.dto.utils.ConversaoVisitantesDto;
import eventify.api_spring.dto.utils.PrecisaoFormularioDto;
import eventify.api_spring.dto.utils.RetencaoDto;
import eventify.api_spring.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eventify.api_spring.exception.http.NoContentException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioAdminDto> pegarListaUsuarios() {
        List<UsuarioAdminDto> usuarios = usuarioRepository.findAllUsuarioLista();

        if (usuarios.isEmpty()) {
            throw new NoContentException("Não há usuários cadastrados");
        }

        return usuarios;
    }

    public List<UsuarioBanidoDto> pegarUsuariosBanidos() {
        Query query = entityManager.createNativeQuery(String.format("SELECT nome, tipo_usuario, cpf FROM usuario WHERE is_banido = 1 ORDER BY data_criacao;"));
        List<Object[]> resultList = query.getResultList();

        List<UsuarioBanidoDto> usuarios = new ArrayList<>();
        for (Object[] result : resultList) {
            Object nome = result[0];
            Object tipoUsuario = result[1];
            Object cpf = result[2];

            usuarios.add(new UsuarioBanidoDto(nome.toString(), tipoUsuario.toString(), cpf.toString()));
        }

        if (usuarios.isEmpty()) {
            throw new NoContentException("Não há usuários banidos");
        }

        return usuarios;
    }

    // método recursivo para mapear os endereços
    private List<EnderecoDto> mapearEndereco(List<Object[]> resultList, List<EnderecoDto> enderecos, int index) {

        if (index == resultList.size()) {
            return enderecos;
        }

        Object[] result = resultList.get(index);

        Object id = result[0];
        Object isValidado = result[1];
        Object logradouro = result[2];
        Object numero = result[3];
        Object bairro = result[4];
        Object cidade = result[5];
        Object uf = result[6];
        Object cep = result[7];
        Object latitude = result[8];
        Object longitude = result[9];
        Object dataCriacao = result[10];

        enderecos.add(new EnderecoDto(id, isValidado, logradouro, numero, bairro, cidade, uf, cep, latitude, longitude, dataCriacao));

        return mapearEndereco(resultList, enderecos, index + 1);
    }

    public List<EnderecoDto> pegarListaEndereco() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM endereco WHERE is_validado = 0;"));
        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new NoContentException("Não há endereços cadastrados");
        }

        List<EnderecoDto> enderecos = new ArrayList<>();

        enderecos = mapearEndereco(resultList, enderecos, 0);

        if (enderecos.isEmpty()) {
            System.out.println("Não há endereços cadastrados");
            throw new NoContentException("Não há endereços cadastrados");
        }

        return enderecos;
    }

    public List<ConversaoVisitantesDto> pegarConversaoCadastros() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_kpi_conversao_de_visitantes"));
        List<Object[]> resultList = query.getResultList();

        List<ConversaoVisitantesDto> conversaoVisitantes = new ArrayList<>();
        for (Object[] result : resultList) {
            Object qtdCadastrados = result[0];
            Object qtdVisitantes = result[1];

            conversaoVisitantes.add(new ConversaoVisitantesDto(qtdCadastrados, qtdVisitantes));
        }

        if (conversaoVisitantes.isEmpty()) {
            throw new NoContentException("Não há conversão de cadastros");
        }

        return conversaoVisitantes;
    }

    public List<PrecisaoFormularioDto> pegarPrecisaoFormulario() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_kpi_precisao_do_formulario"));
        List<Object[]> resultList = query.getResultList();

        List<PrecisaoFormularioDto> precisaoFormulario = new ArrayList<>();
        for (Object[] result : resultList) {
            Object qtdContratosFormularioDinamico = result[0];
            Object qtdContratosTotais = result[1];

            precisaoFormulario.add(new PrecisaoFormularioDto(qtdContratosFormularioDinamico, qtdContratosTotais));
        }

        if (precisaoFormulario.isEmpty()) {
            throw new NoContentException("Não há precisão do formulário");
        }

        return precisaoFormulario;
    }

    public List<ConversaoReservasDto> pegarConversaoReservas() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_kpi_conversao_de_reservas"));
        List<Object[]> resultList = query.getResultList();

        List<ConversaoReservasDto> conversaoReservas = new ArrayList<>();
        for (Object[] result : resultList) {
            Object qtdOrcamentosFechados = result[0];
            Object qtdOrcamentosTotais = result[1];

            conversaoReservas.add(new ConversaoReservasDto(qtdOrcamentosFechados, qtdOrcamentosTotais));
        }

        if (conversaoReservas.isEmpty()) {
            throw new NoContentException("Não há conversão de reservas");
        }

        return conversaoReservas;
    }

    public List<ChurnDto> pegarChurn() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_churn"));
        List<Object[]> resultList = query.getResultList();

        List<ChurnDto> churns = new ArrayList<>();
        for (Object[] result : resultList) {
            Object mes = result[0];
            Object propEntraram = result[1];
            Object propSairam = result[2];
            Object contrEntraram = result[3];
            Object contrSairam = result[4];

            churns.add(new ChurnDto(mes, propEntraram, propSairam, contrEntraram, contrSairam));
        }

        if (churns.isEmpty()) {
            throw new NoContentException("Não há churn");
        }

        return churns;
    }

    public List<RetencaoDto> pegarRetencaoBuffets() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_retencao_buffets_retidos"));
        List<Object[]> resultList = query.getResultList();

        List<RetencaoDto> retencaoBuffets = new ArrayList<>();
        for (Object[] result : resultList) {
            Object mes = result[0];
            Object totalRetidos = result[1];

            retencaoBuffets.add(new RetencaoDto(mes, totalRetidos));
        }

        if (retencaoBuffets.isEmpty()) {
            throw new NoContentException("Não há retenção de buffets");
        }

        return retencaoBuffets;
    }

    public List<RetencaoDto> pegarRetencaoFormulario() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_retencao_formularios_retidos"));
        List<Object[]> resultList = query.getResultList();

        List<RetencaoDto> retencaoFormularios = new ArrayList<>();
        for (Object[] result : resultList) {
            Object mes = result[0];
            Object totalRetidos = result[1];

            retencaoFormularios.add(new RetencaoDto(mes, totalRetidos));
        }

        if (retencaoFormularios.isEmpty()) {
            throw new NoContentException("Não há retenção de buffets");
        }

        return retencaoFormularios;
    }

    public List<RetencaoDto> pegarRetencaoUsuarios() {
        Query query = entityManager.createNativeQuery(String.format("SELECT * FROM vw_retencao_usuarios_retidos"));
        List<Object[]> resultList = query.getResultList();

        List<RetencaoDto> retencaoUsuarios = new ArrayList<>();
        for (Object[] result : resultList) {
            Object mes = result[0];
            Object totalRetidos = result[1];

            retencaoUsuarios.add(new RetencaoDto(mes, totalRetidos));
        }

        if (retencaoUsuarios.isEmpty()) {
            throw new NoContentException("Não há retenção de buffets");
        }

        return retencaoUsuarios;
    }
}