package eventify.api_spring.service.pagamento;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPException;
import org.springframework.stereotype.Service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import java.math.BigDecimal;

@Service
public class PagamentoService {

    public void criarPagamento(String token){
        MercadoPagoConfig.setAccessToken("TEST-8650731281407822-091808-5a0a85a6da9b9601e6f1fb37a50569b4-570528512");

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(new BigDecimal(1000))
                        .token("your_cardtoken")
                        .description("description")
                        .installments(1)
                        .paymentMethodId("visa")
                        .payer(PaymentPayerRequest.builder().email("victor.s.marquess@gmail.com").build())
                        .build();

        try {
            Payment payment = client.create(createRequest);
            System.out.println(payment);
        } catch (MPApiException ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
        } catch (MPException ex) {
            ex.printStackTrace();
        }
    }
}
