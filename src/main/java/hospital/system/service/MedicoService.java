package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Medico;
import hospital.system.repository.CompromissoRepository;
import hospital.system.repository.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository repository;
    private final CompromissoRepository compromissoRepository;

    public MedicoService(MedicoRepository repository, CompromissoRepository compromissoRepository) {
        this.repository = repository;
        this.compromissoRepository = compromissoRepository;
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
        // Antes de excluir, garante que não existam compromissos apontando para
        // este médico. Sem essa checagem, o compromisso fica "órfão" (com um
        // medico_id que não existe mais) e qualquer listagem posterior quebra
        // com JpaObjectRetrievalFailureException.
        if (compromissoRepository.existsByMedicoId(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir este médico: existem compromissos vinculados a ele.");
        }
        repository.deleteById(id);
    }
}