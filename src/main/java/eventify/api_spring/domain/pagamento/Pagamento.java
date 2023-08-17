package eventify.api_spring.domain.pagamento;
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

    public Pagamento(boolean isPagoContrato, boolean isPagoBuffet, LocalDateTime dataPago) {
        this.isPagoContrato = isPagoContrato;
        this.isPagoBuffet = isPagoBuffet;
        this.dataPago = dataPago;
    }

    public Pagamento() {
    }

    public boolean isIsPagoContrato() {
        return this.isPagoContrato;
    }

    public boolean getIsPagoContrato() {
        return this.isPagoContrato;
    }

    public void setIsPagoContrato(boolean isPagoContrato) {
        this.isPagoContrato = isPagoContrato;
    }

    public boolean isIsPagoBuffet() {
        return this.isPagoBuffet;
    }

    public boolean getIsPagoBuffet() {
        return this.isPagoBuffet;
    }

    public void setIsPagoBuffet(boolean isPagoBuffet) {
        this.isPagoBuffet = isPagoBuffet;
    }

    public LocalDateTime getDataPago() {
        return this.dataPago;
    }

    public void setDataPago(LocalDateTime dataPago) {
        this.dataPago = dataPago;
    }

}
