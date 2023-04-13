package api.eventify.backend.Models;

import javax.persistence.*;

@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double valorOrcado;
    private boolean isAceitoProprietario;
    private boolean isAceitoContratante;
    private String motivoNaoAceito;
    @OneToOne
    private Contratante contratante;
    @OneToOne
    private Buffet buffet;

    public Orcamento(Integer id, Double valorOrcado, boolean isAceitoProprietario, boolean isAceitoContratante, String motivoNaoAceito, Contratante contratante, Buffet buffet) {
        this.id = id;
        this.valorOrcado = valorOrcado;
        this.isAceitoProprietario = isAceitoProprietario;
        this.isAceitoContratante = isAceitoContratante;
        this.motivoNaoAceito = motivoNaoAceito;
        this.contratante = contratante;
        this.buffet = buffet;
    }

    public Orcamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorOrcado() {
        return valorOrcado;
    }

    public void setValorOrcado(Double valorOrcado) {
        this.valorOrcado = valorOrcado;
    }

    public boolean isAceitoProprietario() {
        return isAceitoProprietario;
    }

    public void setAceitoProprietario(boolean aceitoProprietario) {
        isAceitoProprietario = aceitoProprietario;
    }

    public boolean isAceitoContratante() {
        return isAceitoContratante;
    }

    public void setAceitoContratante(boolean aceitoContratante) {
        isAceitoContratante = aceitoContratante;
    }

    public String getMotivoNaoAceito() {
        return motivoNaoAceito;
    }

    public void setMotivoNaoAceito(String motivoNaoAceito) {
        this.motivoNaoAceito = motivoNaoAceito;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public void setBuffet(Buffet buffet) {
        this.buffet = buffet;
    }
}
