package hospital.system.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hospital.system.model.Compromisso;

@Repository
public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {

    List<Compromisso> findByMedicoIdAndData(Long medicoId, LocalDate data);

    List<Compromisso> findByPacienteId(Long pacienteId);

    boolean existsByMedicoIdAndDataAndHora(
            Long medicoId,
            LocalDate data,
            LocalTime hora
    );

    boolean existsByMedicoId(Long medicoId);

    boolean existsByPacienteId(Long pacienteId);

    boolean existsByPacienteIdAndDataAndHora(Long pacienteId, LocalDate data, LocalTime hora);

    long countByMedicoIdAndData(Long medicoId, LocalDate data);
}