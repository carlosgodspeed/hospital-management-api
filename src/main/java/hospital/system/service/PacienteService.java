package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Paciente;
import hospital.system.repository.CompromissoRepository;
import hospital.system.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository repository;
    private final CompromissoRepository compromissoRepository;

    public PacienteService(PacienteRepository repository, CompromissoRepository compromissoRepository) {
        this.repository = repository;
        this.compromissoRepository = compromissoRepository;
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
        // Mesma proteção aplicada em MedicoService: evita compromissos órfãos.
        if (compromissoRepository.existsByPacienteId(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir este paciente: existem compromissos vinculados a ele.");
        }
        repository.deleteById(id);
    }
}