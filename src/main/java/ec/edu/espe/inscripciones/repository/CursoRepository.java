package ec.edu.espe.inscripciones.repository;

import ec.edu.espe.inscripciones.entity.Curso;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNombreContainingIgnoreCase(String nombre);

    List<Curso> findByDocenteId(Long docenteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Curso c WHERE c.id = :id")
    Optional<Curso> buscarPorIdParaInscripcion(@Param("id") Long id);
}