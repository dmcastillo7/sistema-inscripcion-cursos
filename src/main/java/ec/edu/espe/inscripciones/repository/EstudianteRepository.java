package ec.edu.espe.inscripciones.repository;

import ec.edu.espe.inscripciones.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {
}