package eventify.api_spring.api.controller;

import eventify.api_spring.api.assets.ListaBuffet;
import eventify.api_spring.domain.*;
import eventify.api_spring.dto.BuffetDtoResposta;
import eventify.api_spring.dto.usuario.BuffetDto;
import eventify.api_spring.service.BuffetService;
import eventify.api_spring.service.PesquisaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hibernate.Hibernate.get;

@RestController
@RequestMapping("/pesquisa")
@Tag(name="3. Pesquisa", description="Controller com os endpoints que controlam as pesquisas do sistema")
public class PesquisaController {
    @Autowired
    private PesquisaService pesquisaService;

    @PostMapping
    private ResponseEntity<List<BuffetDtoResposta>> buscador(@RequestBody Pesquisa p) {
        List<BuffetDtoResposta> listaFiltrada = pesquisaService.getBuffetPorPesquisa(p);

        if((listaFiltrada.size()) == 0){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(listaFiltrada);
    }
}
