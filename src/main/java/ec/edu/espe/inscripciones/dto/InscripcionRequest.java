package ec.edu.espe.inscripciones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class InscripcionRequest {

    @NotNull(message = "Debe seleccionar un estudiante")
    @Positive(message = "El ID del estudiante debe ser positivo")
    private Long estudianteId;

    @NotNull(message = "Debe seleccionar un curso")
    @Positive(message = "El ID del curso debe ser positivo")
    private Long cursoId;

    public InscripcionRequest() {
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}