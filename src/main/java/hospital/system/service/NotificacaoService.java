package hospital.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hospital.system.model.Medico;
import hospital.system.model.Notificacao;
import hospital.system.model.Paciente;
import hospital.system.repository.MedicoRepository;
import hospital.system.repository.NotificacaoRepository;
import hospital.system.repository.PacienteRepository;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final EmailService emailService;

    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              PacienteRepository pacienteRepository,
                              MedicoRepository medicoRepository,
                              EmailService emailService) {
        this.notificacaoRepository = notificacaoRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.emailService = emailService;
    }

    public Notificacao notificarPaciente(Long pacienteId, String mensagem) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Notificacao notificacao = new Notificacao();
        notificacao.setPaciente(paciente);
        notificacao.setMensagem(mensagem);

        Notificacao salva = notificacaoRepository.save(notificacao);

        System.out.println("Notificação para paciente " + paciente.getNome() + ": " + mensagem);

        emailService.enviarEmail(
                paciente.getEmail(),
                "Hospital Management - Atualização sobre seu compromisso",
                mensagem
        );

        return salva;
    }

    public Notificacao notificarMedico(Long medicoId, String mensagem) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        Notificacao notificacao = new Notificacao();
        notificacao.setMedico(medico);
        notificacao.setMensagem(mensagem);

        Notificacao salva = notificacaoRepository.save(notificacao);

        System.out.println("Notificação para médico " + medico.getNome() + ": " + mensagem);

        emailService.enviarEmail(
                medico.getUsuario().getUsername() + " (username, Medico não possui campo de e-mail)",
                "Hospital Management - Atualização sobre um compromisso",
                mensagem
        );

        return salva;
    }

    public List<Notificacao> listarPorPaciente(Long pacienteId) {
        return notificacaoRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId);
    }

    public List<Notificacao> listarPorMedico(Long medicoId) {
        return notificacaoRepository.findByMedicoIdOrderByDataHoraDesc(medicoId);
    }

    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }

    public long contarNaoLidasPorPaciente(Long pacienteId) {
        return notificacaoRepository.countByPacienteIdAndLidaFalse(pacienteId);
    }

    public long contarNaoLidasPorMedico(Long medicoId) {
        return notificacaoRepository.countByMedicoIdAndLidaFalse(medicoId);
    }
}