package eventify.api_spring.dto;

import java.util.List;
import java.util.Set;

public class BuffetInfoDto {

    private List<String> descricoes;
    private String nome;
    private Double precoMediaDiaria;
    private Double notaMediaAvaliacao;
    private List<String> caminho;

    public BuffetInfoDto(List<String> descricao, String nome, Double precoMediaDiaria, Double notaMediaAvaliacao, List<String> caminho) {
        this.descricoes = descricao;
        this.nome = nome;
        this.precoMediaDiaria = precoMediaDiaria;
        this.notaMediaAvaliacao = notaMediaAvaliacao;
        this.caminho = caminho;
    }

    public List<String> getDescricoes() {
        return descricoes;
    }

    public void setDescricoes(List<String> descricoes) {
        this.descricoes = descricoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoMediaDiaria() {
        return precoMediaDiaria;
    }

    public void setPrecoMediaDiaria(Double precoMediaDiaria) {
        this.precoMediaDiaria = precoMediaDiaria;
    }

    public Double getNotaMediaAvaliacao() {
        return notaMediaAvaliacao;
    }

    public void setNotaMediaAvaliacao(Double notaMediaAvaliacao) {
        this.notaMediaAvaliacao = notaMediaAvaliacao;
    }

    public List<String> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<String> caminho) {
        this.caminho = caminho;
    }

    public void adicionarDescricao(String descricao) {
        descricoes.add(descricao);
    }

    public void adicionarImagem(String imagem) {
        caminho.add(imagem);
    }
}

