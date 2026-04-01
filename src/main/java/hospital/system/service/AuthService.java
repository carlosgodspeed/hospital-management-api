package hospital.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hospital.system.model.Usuario;
import hospital.system.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository repository;

    public AuthService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario login(String username, String password) {
        Optional<Usuario> usuarioOpt = repository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }

        throw new RuntimeException("Usuário ou senha inválidos");
    }
}