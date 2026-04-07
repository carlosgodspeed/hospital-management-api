package hospital.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hospital.system.dto.LoginResponse;
import hospital.system.model.Usuario;
import hospital.system.repository.UsuarioRepository;
import hospital.system.security.JwtUtil;

@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(String username, String password) {
        Optional<Usuario> usuarioOpt = repository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(password)) {

                String token = jwtUtil.generateToken(usuario.getUsername());

                return new LoginResponse(
                    token,
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getRole().name()
                );
            }
        }

        throw new RuntimeException("Usuário ou senha inválidos");
    }
}