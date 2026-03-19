package hospital.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hospital.system.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}