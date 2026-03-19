package hospital.system.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hospital.system.model.Compromisso;
import hospital.system.repository.CompromissoRepository;

@Service
public class CompromissoService {

    private final CompromissoRepository repository;
    private final NotificacaoService notificacaoService;
    public CompromissoService(CompromissoRepository repository, NotificacaoService notificacaoService) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }
    public Compromisso salvarCompromisso(Compromisso compromisso) {
        return repository.save(compromisso);
    }
    public List<Compromisso> listarTodos() {
        return repository.findAll();
    }
    public List<Compromisso> buscarPorMedicoEData(Long medicoId, LocalDate data) {
        return repository.findByMedicoIdAndData(medicoId, data);
    }
    public List<Compromisso> buscarPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }
    public Compromisso remarcarCompromisso(Long id, LocalDate novaData, LocalTime novaHora) {
        Optional<Compromisso> opt = repository.findById(id);
        if(opt.isPresent()) {
            Compromisso c = opt.get();
            c.setData(novaData);
            c.setHora(novaHora);
            Compromisso atualizado = repository.save(c);
            notificacaoService.notificarPaciente(
                c.getPaciente().getNome(),
                "Seu compromisso foi remarcado para " + novaData + " às " + novaHora
            );
            return atualizado;
        } else {
            throw new RuntimeException("Compromisso não encontrado");
        }
    }
    public Compromisso atualizarStatus(Long id, Compromisso.Status status) {
        Optional<Compromisso> opt = repository.findById(id);
        if(opt.isPresent()) {
            Compromisso c = opt.get();
            c.setStatus(status);
            Compromisso atualizado = repository.save(c);
            notificacaoService.notificarPaciente(
                c.getPaciente().getNome(),
                "O status do seu compromisso foi alterado para " + status
            );
            return atualizado;
        } else {
            throw new RuntimeException("Compromisso não encontrado");
        }
    }
}