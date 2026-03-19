package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Paciente;
import hospital.system.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository repository;
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }
    public List<Paciente> listarTodos() {
        return repository.findAll();
    }
    public Paciente buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}