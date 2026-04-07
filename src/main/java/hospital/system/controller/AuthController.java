package hospital.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hospital.system.dto.LoginRequest;
import hospital.system.dto.LoginResponse;
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

        LoginResponse response = service.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }
}