package hospital.system.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.system.model.Usuario;
import hospital.system.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Criar usuário (senha é criptografada com BCrypt dentro do UsuarioService)
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.salvar(usuario));
    }

    // O login foi centralizado em /api/auth/login (AuthController + AuthService),
    // que usa PasswordEncoder.matches() para comparar a senha com o hash salvo.
    // O endpoint duplicado que existia aqui foi removido: além de nunca funcionar
    // (comparava a senha em texto puro com o hash BCrypt), ficava bloqueado pela
    // própria SecurityConfig, que exige ROLE_ADMIN para tudo em /api/usuarios/**.
}