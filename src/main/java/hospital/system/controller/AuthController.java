package hospital.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.system.dto.LoginRequest;
import hospital.system.dto.LoginResponse;
import hospital.system.model.Usuario;
import hospital.system.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Usuario usuario = service.login(request.getUsername(), request.getPassword());

        LoginResponse response = new LoginResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole().name()
        );

        return ResponseEntity.ok(response);
    }
}