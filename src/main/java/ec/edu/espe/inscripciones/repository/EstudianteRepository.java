package ec.edu.espe.inscripciones.repository;

import ec.edu.espe.inscripciones.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCase(String correo);
}