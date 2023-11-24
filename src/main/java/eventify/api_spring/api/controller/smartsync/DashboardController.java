package eventify.api_spring.api.controller.smartsync;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "requiredAuth")
@RestController
@RequestMapping("/dashboards")
public class DashboardController {
    
}
