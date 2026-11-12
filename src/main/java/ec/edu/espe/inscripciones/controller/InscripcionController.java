package ec.edu.espe.inscripciones.controller;

import ec.edu.espe.inscripciones.dto.InscripcionRequest;
import ec.edu.espe.inscripciones.entity.Inscripcion;
import ec.edu.espe.inscripciones.service.InscripcionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Inscripcion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Inscripcion> registrar(
            @Valid @RequestBody InscripcionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Inscripcion>> porEstudiante(
            @PathVariable Long estudianteId) {

        return ResponseEntity.ok(
                service.listarPorEstudiante(estudianteId));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Inscripcion>> porCurso(
            @PathVariable Long cursoId) {

        return ResponseEntity.ok(
                service.listarPorCurso(cursoId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Inscripcion>> buscarPorCurso(
            @RequestParam String nombre) {

        return ResponseEntity.ok(
                service.buscarPorNombreCurso(nombre));
    }
}