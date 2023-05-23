package eventify.api_spring.api.controller;

import eventify.api_spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/conversao-cadastros")
    public ResponseEntity<List<Object[]>> pegarConversaoCadastros() {
        List<Object[]> result = adminService.pegarConversaoCadastros();
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

}
