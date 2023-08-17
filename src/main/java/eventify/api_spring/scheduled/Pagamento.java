package eventify.api_spring.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import eventify.api_spring.service.evento.EventoService;

public class Pagamento {
    @Autowired
    private EventoService eventoService;

    @Scheduled(cron = "0 0 0 * * *")
    public void pagamento() {
        System.out.println("[PAGAMENTOS] Iniciando rotina de pagamentos");

        eventoService.pagamentoRotina();

        System.out.println("[PAGAMENTOS] Finalizados com sucesso");
    }
}