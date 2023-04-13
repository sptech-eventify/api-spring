package eventify.sptech.apispring.Controllers;

import eventify.sptech.apispring.Entities.Buffet;
import eventify.sptech.apispring.Repositories.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buffets")
@CrossOrigin(origins = "http://localhost:3000")
public class BuffetController {

    @Autowired
    private BuffetRepository buffetRepository;

    @GetMapping
    public ResponseEntity<List<Buffet>> listar() {
        return ResponseEntity.status(200).body(buffetRepository.findAll());
    }

}
