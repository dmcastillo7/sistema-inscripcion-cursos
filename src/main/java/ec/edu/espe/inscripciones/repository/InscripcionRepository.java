package ec.edu.espe.inscripciones.repository;

import ec.edu.espe.inscripciones.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscripcionRepository
        extends JpaRepository<Inscripcion, Long> {

    boolean existsByEstudianteIdAndCursoId(
            Long estudianteId, Long cursoId);

    long countByCursoId(Long cursoId);

    List<Inscripcion> findByEstudianteId(Long estudianteId);

    List<Inscripcion> findByCursoId(Long cursoId);

    @Query("""
           SELECT i FROM Inscripcion i
           WHERE LOWER(i.curso.nombre) LIKE
                 LOWER(CONCAT('%', :nombre, '%'))
           """)
    List<Inscripcion> buscarPorNombreCurso(
            @Param("nombre") String nombre);
}
