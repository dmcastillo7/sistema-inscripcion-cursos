package ec.edu.espe.inscripciones.controller;

import ec.edu.espe.inscripciones.dto.CursoRequest;
import ec.edu.espe.inscripciones.entity.Curso;
import ec.edu.espe.inscripciones.service.CursoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Curso>> buscarPorNombre(
            @RequestParam String nombre) {

        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Curso>> buscarPorDocente(
            @PathVariable Long docenteId) {

        return ResponseEntity.ok(service.buscarPorDocente(docenteId));
    }

    @PostMapping
    public ResponseEntity<Curso> registrar(
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
