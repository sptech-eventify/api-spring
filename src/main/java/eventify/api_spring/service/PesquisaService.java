package eventify.api_spring.service;

import eventify.api_spring.domain.*;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.mapper.BuffetMapper;
import eventify.api_spring.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesquisaService {
    @Autowired
    private BuffetRepository buffetRepository;

    public static Double calcularDistancia(Double lat1, Double long1, Double lat2, Double long2) {
        // raio médio da Terra em quilômetros
        double raioTerra = 6371;

        // Calcula a diferença de latitude e longitude entre os dois pontos e converte o resultado para radianos.
        Double difLat = Math.toRadians(lat2 - lat1);
        Double difLong = Math.toRadians(long2 - long1);

        // Esta linha calcula um valor intermediário chamado a usando a fórmula de Haversine
        Double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(difLong / 2) * Math.sin(difLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distancia = raioTerra * c;

        return distancia;
    }

    public static boolean calcularOrcamento(Double orcMin, Double orcMax, Double precoMedio) {
        boolean isOrcMinSatisfeito = false;
        boolean isOrcMaxSatisfeito = false;

        // Aplique um calculo no qual, caso o orcMin seja maior do que o precoMedio, o isOrcMinSatisfeito vire true
        if (orcMin > precoMedio * 0.8) {
            isOrcMinSatisfeito = true;
        }

        if(orcMax < precoMedio * 1.2){
            isOrcMinSatisfeito = true;
        }

        return isOrcMinSatisfeito || isOrcMaxSatisfeito;
    }

    public List<BuffetDtoResposta> getBuffetPorPesquisa(Pesquisa p) {
        System.out.println("Pesquisa: " + p.toString());
        List<Buffet> buffets = new ArrayList<>();
        List<BuffetDtoResposta> buffetsFiltrados = new ArrayList<>();
        boolean isTamanhoSatisfeito = false;
        boolean isQtdPessoasSatisfeito = false;
        boolean isFaixaEtariaSatisfeito = false;
        boolean isTipoEventoSatisfeito = false;
        boolean isServicoSatisfeito = false;
        boolean isOrcamentoSatisfeito = false;
        boolean isNomeSatisfeito = false;

        // Este inicializa como verdadeiro para fazer a lógica
        // inversa no formulário dinâmico
        boolean isDistanciaSatisfeito = true;

        // Caso a pesquisa seja apenas pelo nome
        if (!p.getNome().isEmpty()
                && (p.getFaixaEtaria() == null)
                && (p.getQtdPessoas() == null)
                && (p.getTipoEvento() == null)
                && (p.getOrcMax() == null)
                && (p.getOrcMin() == null)
                && (p.getDataEvento() == null)
                && (p.getServico() == null)
                && (p.getLatitude() == null
                && (p.getLongitude() == null))) {
            buffets = buffetRepository.findAllByNomeContaining(p.getNome());
            return buffets.stream().map(BuffetMapper::toDto).collect(Collectors.toList());
        }else{
            if(p.getNome() != null){
                buffets = buffetRepository.findAllByNomeContaining(p.getNome());
            }else{
                buffets = buffetRepository.findAllBuffet();
            }
        }

        for (int i = 0; i < buffets.size(); i++) {
            if(p.getTipoEvento() != null){
                for (TipoEvento tp : buffets.get(i).getTiposEventos()) {
                    if (p.getTipoEvento().contains(tp.getDescricao())) {
                        isTipoEventoSatisfeito = true;
                    }
                }
            }else{
                isTipoEventoSatisfeito = true;
            }

            if(p.getFaixaEtaria() != null) {
                for (FaixaEtaria fe : buffets.get(i).getFaixaEtarias()) {
                    if (fe.getDescricao().equals(p.getFaixaEtaria())) {
                        isFaixaEtariaSatisfeito = true;
                    }
                }
            }else{
                isFaixaEtariaSatisfeito = true;
            }

            if(p.getServico() != null) {
                for (Servico se : buffets.get(i).getServicos()) {
                    if (se.getDescricao().equals(p.getServico())) {
                        isServicoSatisfeito = true;
                    }
                }
            }else{
                isServicoSatisfeito = true;
            }

            if(p.getQtdPessoas() != null) {
                if (p.getQtdPessoas() <= buffets.get(i).getQtdPessoas()) {
                    isQtdPessoasSatisfeito = true;
                } else {
                    isQtdPessoasSatisfeito = false;
                }
            }else{
                isQtdPessoasSatisfeito = true;
            }

            if(p.getLatitude() != null && p.getLongitude() != null){
                if (calcularDistancia(p.getLatitude(), p.getLongitude(), buffets.get(i).getEndereco().getLatitude(), buffets.get(i).getEndereco().getLongitude()) > 15) {
                    isDistanciaSatisfeito = false;
                }
            }

            boolean isOrcMinSatisfeito = false;
            boolean isOrcMaxSatisfeito = false;

            if(p.getOrcMin() == null && p.getOrcMax() == null){
                isOrcamentoSatisfeito = true;
            } else {
                if(p.getOrcMin() != null && p.getOrcMax() != null){
                    if (calcularOrcamento(p.getOrcMin(), p.getOrcMax(), buffets.get(i).getPrecoMedioDiaria())) {
                        isOrcamentoSatisfeito = true;
                    }
                }else{
                    if(p.getOrcMin() != null){
                        if(p.getOrcMin() <= buffets.get(i).getPrecoMedioDiaria()){
                            isOrcamentoSatisfeito = true;
                        }
                    }else{
                        if(p.getOrcMax() != null){
                            if(p.getOrcMax() >= buffets.get(i).getPrecoMedioDiaria()){
                                isOrcamentoSatisfeito = true;
                            }
                        }
                    }
                }
            }

            if(p.getTamanho() != null) {
                if (p.getTamanho() <= buffets.get(i).getTamanho()) {
                    isTamanhoSatisfeito = true;
                }
            }else{
                isTamanhoSatisfeito = true;
            }


            if(isTamanhoSatisfeito
                && isDistanciaSatisfeito
                && isFaixaEtariaSatisfeito
                && isTipoEventoSatisfeito
                && isServicoSatisfeito
                && isOrcamentoSatisfeito
                && isQtdPessoasSatisfeito) {
                buffetsFiltrados.add(BuffetMapper.toDto(buffets.get(i)));
            }
        }

        return buffetsFiltrados;
    }


}
