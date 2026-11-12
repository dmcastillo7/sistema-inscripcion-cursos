package ec.edu.espe.inscripciones.repository;

import ec.edu.espe.inscripciones.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNombreContainingIgnoreCase(String nombre);

    List<Curso> findByDocenteId(Long docenteId);
}