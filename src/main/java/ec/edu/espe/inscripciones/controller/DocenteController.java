package ec.edu.espe.inscripciones.controller;

import ec.edu.espe.inscripciones.dto.DocenteRequest;
import ec.edu.espe.inscripciones.entity.Docente;
import ec.edu.espe.inscripciones.service.DocenteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService service;

    public DocenteController(DocenteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Docente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Docente> registrar(
            @Valid @RequestBody DocenteRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DocenteRequest request) {

        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}