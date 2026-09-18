package hospital.system.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hospital.system.model.Compromisso;
import hospital.system.model.Medico;
import hospital.system.model.Paciente;
import hospital.system.repository.CompromissoRepository;
import hospital.system.repository.MedicoRepository;
import hospital.system.repository.PacienteRepository;

@Service
public class CompromissoService {

    private final CompromissoRepository repository;
    private final NotificacaoService notificacaoService;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CompromissoService(
            CompromissoRepository repository,
            NotificacaoService notificacaoService,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {

        this.repository = repository;
        this.notificacaoService = notificacaoService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Compromisso salvarCompromisso(Compromisso compromisso) {

        // Validação de "data futura" feita aqui manualmente (e não via @Future
        // na entidade) porque o Hibernate revalida a entidade inteira em TODO
        // save(), inclusive em updates de status/remarcação. Se ficasse na
        // entidade, um compromisso cuja data já passou nunca mais poderia ter
        // o status alterado (ver bug documentado no Readme).
        if (compromisso.getData() != null && !compromisso.getData().isAfter(LocalDate.now())) {
            throw new RuntimeException("A data do compromisso deve ser no futuro");
        }

        boolean horarioOcupado =
                repository.existsByMedicoIdAndDataAndHora(
                        compromisso.getMedico().getId(),
                        compromisso.getData(),
                        compromisso.getHora()
                );

        if (horarioOcupado) {
            throw new RuntimeException(
                    "Já existe um compromisso para este médico neste horário."
            );
        }

        // Verificar se paciente já tem compromisso no mesmo horário
        if (repository.existsByPacienteIdAndDataAndHora(
                compromisso.getPaciente().getId(),
                compromisso.getData(),
                compromisso.getHora())) {
            throw new IllegalStateException("Paciente já possui compromisso neste horário");
        }

        // Verificar se médico já tem 12 compromissos no mesmo dia
        long compromissosDoMedico = repository.countByMedicoIdAndData(
                compromisso.getMedico().getId(),
                compromisso.getData());
        if (compromissosDoMedico >= 12) {
            throw new IllegalStateException("Médico atingiu o limite de 12 compromissos neste dia");
        }

        Compromisso salvo = repository.save(compromisso);

        // Busca as entidades completas no banco para montar as mensagens de
        // notificação com nome de verdade. O objeto "salvo" só tem os campos
        // que o cliente mandou no corpo da requisição (normalmente só o id),
        // então salvo.getMedico().getNome() viria null se usássemos direto.
        Paciente pacienteCompleto = pacienteRepository.findById(salvo.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Medico medicoCompleto = medicoRepository.findById(salvo.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        notificacaoService.notificarPaciente(
                pacienteCompleto.getId(),
                "Sua consulta com Dr(a). " + medicoCompleto.getNome()
                        + " foi agendada para " + salvo.getData() + " às " + salvo.getHora()
        );

        notificacaoService.notificarMedico(
                medicoCompleto.getId(),
                "Nova consulta agendada com o paciente " + pacienteCompleto.getNome()
                        + " para " + salvo.getData() + " às " + salvo.getHora()
        );

        return salvo;
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

    public Compromisso remarcarCompromisso(
            Long id,
            LocalDate novaData,
            LocalTime novaHora) {

        if (novaData != null && !novaData.isAfter(LocalDate.now())) {
            throw new RuntimeException("A nova data do compromisso deve ser no futuro");
        }

        Optional<Compromisso> opt = repository.findById(id);

        if (opt.isPresent()) {

            Compromisso c = opt.get();

            c.setData(novaData);
            c.setHora(novaHora);

            Compromisso atualizado = repository.save(c);

            notificacaoService.notificarPaciente(
                    c.getPaciente().getId(),
                    "Seu compromisso foi remarcado para "
                            + novaData + " às " + novaHora
            );

            return atualizado;
        }

        throw new RuntimeException("Compromisso não encontrado");
    }

    public Compromisso atualizarStatus(Long id, Compromisso.Status status) {

        Optional<Compromisso> opt = repository.findById(id);

        if (opt.isPresent()) {

            Compromisso c = opt.get();

            c.setStatus(status);

            Compromisso atualizado = repository.save(c);

            notificacaoService.notificarPaciente(
                    c.getPaciente().getId(),
                    "O status do seu compromisso foi alterado para " + status
            );

            return atualizado;
        }

        throw new RuntimeException("Compromisso não encontrado");
    }
}