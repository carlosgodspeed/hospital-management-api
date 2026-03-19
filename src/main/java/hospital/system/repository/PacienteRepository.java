package hospital.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hospital.system.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}