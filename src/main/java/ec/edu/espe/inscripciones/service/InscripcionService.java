package ec.edu.espe.inscripciones.service;

import ec.edu.espe.inscripciones.dto.InscripcionRequest;
import ec.edu.espe.inscripciones.entity.Curso;
import ec.edu.espe.inscripciones.entity.Estudiante;
import ec.edu.espe.inscripciones.entity.Inscripcion;
import ec.edu.espe.inscripciones.exception.RecursoNoEncontradoException;
import ec.edu.espe.inscripciones.exception.ReglaNegocioException;
import ec.edu.espe.inscripciones.repository.CursoRepository;
import ec.edu.espe.inscripciones.repository.EstudianteRepository;
import ec.edu.espe.inscripciones.repository.InscripcionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public InscripcionService(
            InscripcionRepository inscripcionRepository,
            EstudianteRepository estudianteRepository,
            CursoRepository cursoRepository) {

        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> listar() {
        return inscripcionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Inscripcion buscarPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe la inscripción con ID: " + id));
    }

    @Transactional
    public Inscripcion registrar(InscripcionRequest request) {

        Estudiante estudiante = estudianteRepository
                .findById(request.getEstudianteId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "El estudiante no existe"));

        // Bloqueamos el curso para serializar las inscripciones
        // concurrentes que compiten por sus cupos.
        Curso curso = cursoRepository
                .buscarPorIdParaInscripcion(request.getCursoId())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "El curso no existe"));

        boolean duplicado = inscripcionRepository
                .existsByEstudianteIdAndCursoId(
                        estudiante.getId(), curso.getId());

        if (duplicado) {
            throw new ReglaNegocioException(
                    "El estudiante ya está inscrito en este curso");
        }

        long inscritos = inscripcionRepository
                .countByCursoId(curso.getId());

        if (inscritos >= curso.getCupoMaximo()) {
            throw new ReglaNegocioException(
                    "El curso no tiene cupos disponibles");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);

        return inscripcionRepository.save(inscripcion);
    }

    @Transactional
    public void eliminar(Long id) {
        Inscripcion inscripcion = buscarPorId(id);
        inscripcionRepository.delete(inscripcion);
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> listarPorEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId);
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> listarPorCurso(Long cursoId) {
        return inscripcionRepository.findByCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> buscarPorNombreCurso(String nombre) {
        return inscripcionRepository.buscarPorNombreCurso(nombre);
    }
}
