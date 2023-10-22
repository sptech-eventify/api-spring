package eventify.api_spring.service.buffet;

import eventify.api_spring.domain.buffet.Transacao;
import eventify.api_spring.exception.http.NoContentException;
import eventify.api_spring.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> exibirTodasTransacoes(){
        List<Transacao> transacaos = transacaoRepository.findAll();

        if (transacaos.isEmpty()){
            throw new NoContentException("Não há transações cadastradas");
        }

        return transacaos;
    }

    public Transacao exibirTransacaoPorId(Integer id){
        Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new NoContentException("Transação não encontrada"));

        return transacao;
    }

    public List<Transacao> exibirTodasTransacoesPorBuffetId(Integer idBuffet){
        List<Transacao> transacaos = transacaoRepository.findAllByBuffetId(idBuffet);

        if (transacaos.isEmpty()){
            throw new NoContentException("Não há transações cadastradas");
        }

        return transacaos;
    }

    public Transacao criarTransacao(Transacao transacao){
        return transacaoRepository.save(transacao);
    }

    public Transacao atualizarTransacao(Integer id){
        Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new NoContentException("Transação não encontrada"));

        return transacaoRepository.save(transacao);
    }

    public void deletarTransacao(Integer id){
        transacaoRepository.deleteById(id);
    }
}
