package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Notificacao;
import hospital.system.model.Paciente;
import hospital.system.repository.NotificacaoRepository;
import hospital.system.repository.PacienteRepository;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final PacienteRepository pacienteRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              PacienteRepository pacienteRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Cria e persiste uma notificação para um paciente.
     * Mantém o log no console (não remove nada que já funcionava),
     * mas agora também grava a notificação na tabela `notificacoes`.
     */
    public Notificacao notificarPaciente(Long pacienteId, String mensagem) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Notificacao notificacao = new Notificacao();
        notificacao.setPaciente(paciente);
        notificacao.setMensagem(mensagem);

        Notificacao salva = notificacaoRepository.save(notificacao);

        System.out.println("Notificação para " + paciente.getNome() + ": " + mensagem);

        return salva;
    }

    public List<Notificacao> listarPorPaciente(Long pacienteId) {
        return notificacaoRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId);
    }

    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }
}