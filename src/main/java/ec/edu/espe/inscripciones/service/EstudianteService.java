package ec.edu.espe.inscripciones.service;

import ec.edu.espe.inscripciones.dto.EstudianteRequest;
import ec.edu.espe.inscripciones.entity.Estudiante;
import ec.edu.espe.inscripciones.exception.CorreoDuplicadoException;
import ec.edu.espe.inscripciones.exception.RecursoNoEncontradoException;
import ec.edu.espe.inscripciones.repository.EstudianteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Estudiante buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el estudiante con ID: " + id));
    }

    @Transactional
    public Estudiante registrar(EstudianteRequest request) {

        String correo = request.getCorreo().trim();

        if (repository.existsByCorreoIgnoreCase(correo)) {
            throw new CorreoDuplicadoException(
                    "Ya existe un estudiante con ese correo");
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.getNombre().trim());
        estudiante.setCorreo(correo);

        return repository.save(estudiante);
    }

    @Transactional
    public Estudiante actualizar(Long id, EstudianteRequest request) {

        Estudiante estudiante = buscarPorId(id);
        String correo = request.getCorreo().trim();

        repository.findByCorreoIgnoreCase(correo)
                .ifPresent(existente -> {
                    if (!existente.getId().equals(id)) {
                        throw new CorreoDuplicadoException(
                                "El correo pertenece a otro estudiante");
                    }
                });

        estudiante.setNombre(request.getNombre().trim());
        estudiante.setCorreo(correo);

        return repository.save(estudiante);
    }

    @Transactional
    public void eliminar(Long id) {

        Estudiante estudiante = buscarPorId(id);

        repository.delete(estudiante);
    }
}