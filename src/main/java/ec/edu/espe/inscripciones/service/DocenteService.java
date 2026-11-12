package ec.edu.espe.inscripciones.service;

import ec.edu.espe.inscripciones.dto.DocenteRequest;
import ec.edu.espe.inscripciones.entity.Docente;
import ec.edu.espe.inscripciones.exception.CorreoDuplicadoException;
import ec.edu.espe.inscripciones.exception.RecursoNoEncontradoException;
import ec.edu.espe.inscripciones.repository.DocenteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class DocenteService {

    private final DocenteRepository repository;

    public DocenteService(DocenteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Docente> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Docente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el docente con ID: " + id));
    }

    @Transactional
    public Docente registrar(DocenteRequest request) {

        String correo = normalizarCorreo(request.getCorreo());

        if (repository.existsByCorreoIgnoreCase(correo)) {
            throw new CorreoDuplicadoException(
                    "Ya existe un docente registrado con ese correo");
        }

        Docente docente = new Docente();
        docente.setNombre(request.getNombre().trim());
        docente.setCorreo(correo);

        return repository.save(docente);
    }

    @Transactional
    public Docente actualizar(Long id, DocenteRequest request) {

        Docente docente = buscarPorId(id);
        String correo = normalizarCorreo(request.getCorreo());

        repository.findByCorreoIgnoreCase(correo)
                .ifPresent(existente -> {
                    if (!existente.getId().equals(id)) {
                        throw new CorreoDuplicadoException(
                                "El correo pertenece a otro docente");
                    }
                });

        docente.setNombre(request.getNombre().trim());
        docente.setCorreo(correo);

        return repository.save(docente);
    }

    @Transactional
    public void eliminar(Long id) {

        Docente docente = buscarPorId(id);
        repository.delete(docente);
        repository.flush();
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase(Locale.ROOT);
    }
}