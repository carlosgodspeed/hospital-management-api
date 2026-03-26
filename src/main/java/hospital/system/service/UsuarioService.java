package hospital.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import hospital.system.model.Usuario;
import hospital.system.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}
