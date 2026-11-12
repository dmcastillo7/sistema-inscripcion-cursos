package ec.edu.espe.inscripciones.service;

import ec.edu.espe.inscripciones.dto.CursoRequest;
import ec.edu.espe.inscripciones.entity.Curso;
import ec.edu.espe.inscripciones.entity.Docente;
import ec.edu.espe.inscripciones.exception.RecursoNoEncontradoException;
import ec.edu.espe.inscripciones.repository.CursoRepository;
import ec.edu.espe.inscripciones.repository.DocenteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;

    public CursoService(
            CursoRepository cursoRepository,
            DocenteRepository docenteRepository) {

        this.cursoRepository = cursoRepository;
        this.docenteRepository = docenteRepository;
    }

    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el curso con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Curso> buscarPorNombre(String nombre) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    public List<Curso> buscarPorDocente(Long docenteId) {
        return cursoRepository.findByDocenteId(docenteId);
    }

    @Transactional
    public Curso registrar(CursoRequest request) {

        Docente docente = obtenerDocente(request.getDocenteId());

        Curso curso = new Curso();
        asignarDatos(curso, request, docente);

        return cursoRepository.save(curso);
    }

    @Transactional
    public Curso actualizar(Long id, CursoRequest request) {

        Curso curso = buscarPorId(id);
        Docente docente = obtenerDocente(request.getDocenteId());

        asignarDatos(curso, request, docente);

        return cursoRepository.save(curso);
    }

    @Transactional
    public void eliminar(Long id) {

        Curso curso = buscarPorId(id);
        cursoRepository.delete(curso);
        cursoRepository.flush();
    }

    private Docente obtenerDocente(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el docente con ID: " + id));
    }

    private void asignarDatos(
            Curso curso,
            CursoRequest request,
            Docente docente) {

        curso.setNombre(request.getNombre().trim());
        curso.setDescripcion(request.getDescripcion());
        curso.setCupoMaximo(request.getCupoMaximo());
        curso.setDocente(docente);
    }
}