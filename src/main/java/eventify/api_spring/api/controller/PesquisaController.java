package eventify.api_spring.api.controller;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Pesquisa;
import eventify.api_spring.service.BuffetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pesquisa")
@Tag(name="3. Pesquisa", description="Controller com os endpoints que controlam as pesquisas do sistema")
public class PesquisaController {
    @Autowired
    private BuffetService buffetService;

    @GetMapping()
    private ResponseEntity<List<Buffet>> buscarBuffetProximo(@RequestBody Pesquisa p) {
        List<Buffet> buffets = buffetService.listar();
        List<Buffet> buffetsFiltrados = new ArrayList<>();

        for (int i = 0; i < buffets.size(); i++) {
            boolean isDentro = true;

            if (!buffets.get(i).getTiposEventos().contains(p.getTipoEvento())) {
                isDentro = false;
            }

            if (!buffets.get(i).getFaixaEtarias().contains(p.getFaixaEtaria())) {
                isDentro = false;
            }

            if (calcularDistancia(p.getLatitude(), p.getLongitude(), buffets.get(i).getEndereco().getLatitude(), buffets.get(i).getEndereco().getLongitude()) > 15) {
                isDentro = false;
            }

            if (isDentro) {
                buffetsFiltrados.add(buffets.get(i));
            }
        }

        return ResponseEntity.ok().body(buffetsFiltrados);
    }
    public static double calcularDistancia(double lat1, double long1, double lat2, double long2) {
        // raio médio da Terra em quilômetros
        double raioTerra = 6371;

        // Calcula a diferença de latitude e longitude entre os dois pontos e converte o resultado para radianos.
        double difLat = Math.toRadians(lat2 - lat1);
        double difLong = Math.toRadians(long2 - long1);

        // Esta linha calcula um valor intermediário chamado a usando a fórmula de Haversine
        double a = Math.sin(difLat / 2) * Math.sin(difLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(difLong / 2) * Math.sin(difLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia = raioTerra * c;
        return distancia;
    }
}
