package ec.edu.espe.inscripciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DocenteRequest {

    @NotBlank(message = "El nombre del docente es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo del docente es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private String correo;

    public DocenteRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}