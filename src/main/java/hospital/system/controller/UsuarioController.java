package hospital.system.controller;

import java.util.Optional;

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
    // Criar usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.salvar(usuario));
    }
    // Login simples
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> user = service.buscarPorUsername(usuario.getUsername());

        if(user.isPresent() && user.get().getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}