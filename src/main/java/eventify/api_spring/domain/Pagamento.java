package eventify.api_spring.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isPagoContrato;
    private boolean isPagoBuffet;
    private LocalDateTime dataPago;

    public Pagamento(Integer id, boolean isPagoContrato, boolean isPagoBuffet, LocalDateTime dataPago) {
        this.id = id;
        this.isPagoContrato = isPagoContrato;
        this.isPagoBuffet = isPagoBuffet;
        this.dataPago = dataPago;
    }

    public Pagamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPagoContrato() {
        return isPagoContrato;
    }

    public void setPagoContrato(boolean pagoContrato) {
        isPagoContrato = pagoContrato;
    }

    public boolean isPagoBuffet() {
        return isPagoBuffet;
    }

    public void setPagoBuffet(boolean pagoBuffet) {
        isPagoBuffet = pagoBuffet;
    }

    public LocalDateTime getDataPago() {
        return dataPago;
    }

    public void setDataPago(LocalDateTime dataPago) {
        this.dataPago = dataPago;
    }
}
