package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Medico;
import hospital.system.repository.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository repository;
    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }
    public Medico salvar(Medico medico) {
        return repository.save(medico);
    }
    public List<Medico> listarTodos() {
        return repository.findAll();
    }
    public Medico buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}